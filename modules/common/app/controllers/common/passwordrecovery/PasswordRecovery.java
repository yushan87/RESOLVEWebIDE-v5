/**
 * ---------------------------------
 * Copyright (c) 2016
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package controllers.common.passwordrecovery;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.common.email.EmailGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import models.common.database.User;
import models.common.form.PasswordRecoveryForm;
import models.common.form.UpdatePasswordForm;
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
import views.html.common.errors.accountError;
import views.html.common.passwordrecovery.passwordRecovery;
import views.html.common.passwordrecovery.resetSuccess;
import views.html.common.passwordrecovery.submitSuccess;
import views.html.common.passwordrecovery.updatePassword;

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

    /** <p>Email generator</p> */
    @Inject
    private EmailGenerator myEmailGenerator;

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
     * <p>This renders the password recovery page with the
     * {@link PasswordRecoveryForm} for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    public Result index() {
        String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
        return ok(passwordRecovery.render(myFormFactory.form(PasswordRecoveryForm.class), token));
    }

    /**
     * <p>This handles the {@link PasswordRecoveryForm} submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional
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
                    // Set the user to not authenticated. This should generate a new
                    // confirmation code that gets sent to the user in an email.
                    // Note 1: "setNotAuthenticated" and "authenticate" expects a JPA entity manager,
                    // which is not present if we don't wrap the call using
                    // "withTransaction()".
                    // Note 2: It is possible that that this will fail if we fail to
                    // retrieve data from the database. We are ignoring this for now.
                    User user = myJpaApi.withTransaction(() -> User.setNotAuthenticated(form.getEmail()));
                    myEmailGenerator.generateResetPasswordEmail(user.firstName, user.email, user.confirmationCode);

                    return ok(submitSuccess.render());
                }
            }, myHttpExecutionContext.current());
        }
    }

    /**
     * <p>This renders the update password page with the
     * {@link UpdatePasswordForm} for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @Transactional(readOnly = true)
    public Result updatePassword(String confirmationCode, String email) {
        String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");

        // Check to see if the email exists. If it does not return
        // a valid "User" or the confirmation code does not match, then we
        // display the error page.
        User user = User.findByEmail(email);
        if (user != null && user.confirmationCode.equals(confirmationCode)) {
            // Render password reset success page
            return ok(updatePassword.render(myFormFactory.form(UpdatePasswordForm.class), token, email));
        } else {
            // Render the account error page
            return ok(accountError.render());
        }
    }

    /**
     * <p>This handles the {@link UpdatePasswordForm} submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional
    public CompletionStage<Result> processRequest() {
        Form<UpdatePasswordForm> userForm = myFormFactory.form(UpdatePasswordForm.class).bindFromRequest();

        // Retrieve the email from the form (the input box is hidden).
        String email = userForm.field("email").value();

        // Perform the basic validation checks.
        if (userForm.hasErrors()) {
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
            return CompletableFuture.supplyAsync(() -> badRequest(updatePassword.render(userForm, token, email)),
                    myHttpExecutionContext.current());
        } else {
            UpdatePasswordForm form = userForm.get();

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
                    return badRequest(updatePassword.render(userForm, token, email));
                }
                else {
                    // Set the user to authenticated. This should generate an email
                    // confirming we have successfully reset the user's password.
                    // Note 1: "updatePassword" expects a JPA entity manager,
                    // which is not present if we don't wrap the call using
                    // "withTransaction()".
                    // Note 2: It is possible that that this will fail if we fail to
                    // retrieve data from the database. We are ignoring this for now.
                    User user = myJpaApi.withTransaction(() -> User.updatePassword(email, form.getPassword()));
                    user = myJpaApi.withTransaction(() -> User.authenticate(email));
                    myEmailGenerator.generateResetSuccessEmail(user.firstName, user.email);

                    return ok(resetSuccess.render(email));
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
     * if there are errors in the password recovery form, {@code null} otherwise.
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

    /**
     * <p>Our own custom validation method for the update password form.</p>
     *
     * @param form The current update password form we are processing.
     *
     * @return A {@link CompletionStage} with a list of {@link ValidationError}
     * if there are errors in the update password form, {@code null} otherwise.
     */
    @Transactional(readOnly = true)
    private CompletionStage<List<ValidationError>> validate(UpdatePasswordForm form) {
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

            // Check that the password has a minimum length of 6 and a maximum of 20
            if (form.getPassword().length() < 6 || form.getPassword().length() > 20) {
                errors.add(new ValidationError("passwordLength", "The password must be 6-20 characters long."));
            } else {
                if (!form.getPassword().equals(form.getConfirmPassword())) {
                    errors.add(new ValidationError("notSamePassword", "The two password fields do not match."));
                }
            }

            return errors.isEmpty() ? null : errors;
        }, myHttpExecutionContext.current());
    }

}