package controllers.common.passwordrecovery;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import models.common.database.User;
import models.common.form.PasswordRecoveryForm;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.passwordrecovery.passwordRecovery;
import views.html.common.passwordrecovery.submitSuccess;

/**
 * <p>This class serves as a controller class for recovering password
 * for an existing user.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class PasswordRecovery extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Play Framework's WS API client</p> */
    @Inject
    private WSClient myWSClient;

    /** <p>Form factory</p> */
    @Inject
    private FormFactory myFormFactory;

    /** <p>An executor to get the current HTTP context.</p> */
    @Inject
    private HttpExecutionContext myHttpExecutionContext;

    /** <p>JPA API</p> */
    @Inject
    private JPAApi myJpaApi;

    /** <p>Class that retrieves configurations</p> */
    @Inject
    private Configuration myConfiguration;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the password recovery page for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    public Result index() {
        String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
        return ok(passwordRecovery.render(myFormFactory.form(PasswordRecoveryForm.class), token));
    }

    /**
     * <p>This handles the password recovery form submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional(readOnly = true)
    public CompletionStage<Result> handleSubmit() {
        Form<PasswordRecoveryForm> userForm = myFormFactory.form(PasswordRecoveryForm.class).bindFromRequest();

        // Perform the basic validation checks.
        if (userForm.hasErrors()) {
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
            return CompletableFuture.supplyAsync(() -> badRequest(passwordRecovery.render(userForm, token)),
                    myHttpExecutionContext.current());
        } else {
            PasswordRecoveryForm form = userForm.get();

            // Perform our own validation checks. If we detect errors, then
            // we display the registration page with the errors highlighted.
            // If there are no errors, we display the success page.
            CompletionStage<List<ValidationError>> resultPromise = validate(form);
            return resultPromise.thenApplyAsync(result -> {
                if (result != null) {
                    String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
                    for (ValidationError error : result) {
                        userForm.reject(error);
                    }
                    return badRequest(passwordRecovery.render(userForm, token));
                }
                else {
                    return ok(submitSuccess.render());
                }
            }, myHttpExecutionContext.current());
        }
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Our own custom validation method for the password recovery form.</p>
     *
     * @param form The current password recovery form we are processing.
     *
     * @return A {@link CompletionStage} with a list of {@link ValidationError}
     * if there are errors in the registration form, {@code null} otherwise.
     */
    @Transactional(readOnly = true)
    private CompletionStage<List<ValidationError>> validate(PasswordRecoveryForm form) {
        // Check the reCaptcha server to see if this form contains a valid
        // user response token provided by reCaptcha.
        // Obtain the email host from the configuration file
        String secret = myConfiguration.getString("webide.recaptchasecret");
        if (secret == null) {
            throw new RuntimeException("Missing configuration: Email Host");
        }
        String postData = "secret=" + secret + "&response=" + form.getReCaptcha();
        CompletionStage<JsonNode> responsePromise = myWSClient.url("https://www.google.com/recaptcha/api/siteverify").
                setContentType("application/x-www-form-urlencoded").
                post(postData).thenApply(WSResponse::asJson);

        // Once we obtain a JsonNode response from the reCaptcha server, apply the following
        // code asynchronously. Notice that we have to pass a "HttpExecutionContext" to avoid
        // getting a "No HTTP Context" error.
        return responsePromise.thenApplyAsync(response -> {
            List<ValidationError> errors = new ArrayList<>();

            // Check to see if we got a success response from the reCaptcha server
            if (!response.get("success").asBoolean()) {
                errors.add(new ValidationError("reCaptchaFailure", "The reCaptcha did not succeed."));
            }

            // Check to see if the email exists.
            // Note that "findByEmail" expects a JPA entity manager,
            // which is not present if we don't wrap the call using
            // "withTransaction()".
            ValidationError emailError = myJpaApi.withTransaction(() -> {
                if (User.findByEmail(form.getEmail()) == null) {
                    return new ValidationError("emailNotFound", "The specified e-mail cannot be found.");
                }

                return null;
            });

            // If the result from the "withTransaction" call contains a
            // "ValidationError", then we add it to our list.
            if (emailError != null) {
                errors.add(emailError);
            }

            return errors.isEmpty() ? null : errors;
        }, myHttpExecutionContext.current());
    }

}