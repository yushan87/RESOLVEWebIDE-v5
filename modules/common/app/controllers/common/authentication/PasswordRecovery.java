package controllers.common.authentication;

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
import views.html.common.authentication.passwordRecovery;

/**
 * TODO: Add JavaDocs for this class.
 */
public class PasswordRecovery extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

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
            /*RegistrationForm form = userForm.get();

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
                    return badRequest(registration.render(userForm, token));
                }
                else {
                    return ok(registrationSuccess.render());
                }
            }, myHttpExecutionContext.current());*/

            return CompletableFuture.supplyAsync(() -> ok("Not done!"),
                    myHttpExecutionContext.current());
        }
    }

}