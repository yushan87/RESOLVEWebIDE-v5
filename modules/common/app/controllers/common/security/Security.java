/*
 *  ---------------------------------
 *  Copyright (c) 2017
 *  RESOLVE Software Research Group
 *  School of Computing
 *  Clemson University
 *  All rights reserved.
 *  ---------------------------------
 *  This file is subject to the terms and conditions defined in
 *  file 'LICENSE.txt', which is part of this source code package.
 */

package controllers.common.security;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import controllers.common.email.EmailGenerator;
import models.common.database.User;
import models.common.database.UserEvent;
import models.common.form.LoginForm;
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
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.errors.accountError;
import views.html.common.registration.registrationSuccess;
import views.html.common.security.index;
import views.html.common.security.notAuthenticated;

/**
 * <p>This class serves as a controller class for handling all security
 * related actions including login and logout.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class Security extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

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
     * <p>This renders the login page for the Web IDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    public Result index() {
        // Check the session to see if the request comes from an user
        // that has logged in already.
        String user = session().remove("connected");
        if (user != null) {
            // Obtain the http context from the configuration file
            String context = myConfiguration.getString("play.http.context");
            if (context == null) {
                context = "";
            }

            // Redirect back to the home page
            return redirect(context + "/");
        }
        else {
            // Render the page with the login form
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
            return ok(index.render(myFormFactory.form(LoginForm.class), token));
        }
    }

    /**
     * <p>This handles the login form submission for the Web IDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional
    public CompletionStage<Result> login() {
        Form<LoginForm> userForm = myFormFactory.form(LoginForm.class).bindFromRequest();

        // Perform the basic validation checks.
        if (userForm.hasErrors()) {
            // Render the page with the login form with the errors fields
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
            return CompletableFuture.supplyAsync(() -> badRequest(index.render(userForm, token)),
                    myHttpExecutionContext.current());
        }
        else {
            LoginForm form = userForm.get();

            // Check for a registered user with the same email.
            // Note that "connect" expects a JPA entity manager,
            // which is not present if we don't wrap the call using
            // "withTransaction()".
            User user = myJpaApi.withTransaction(() -> User.connect(form.getEmail(), form.getPassword()));
            if (user != null) {
                // Check to see if this account has been authenticated or not.
                boolean hasAuthenticated = myJpaApi.withTransaction(() -> User.hasAuthenticated(form.getEmail()));
                if (hasAuthenticated) {
                    // Update the login date
                    final User updatedUser = myJpaApi.withTransaction(() -> User.lastLogin(form.getEmail()));

                    // Add a new user event
                    myJpaApi.withTransaction(() -> UserEvent.addRegularEvent("login", "", updatedUser));

                    // Stores the email as session value
                    session("connected", form.getEmail());

                    // Obtain the http context from the configuration file
                    String context = myConfiguration.getString("play.http.context");
                    if (context == null) {
                        context = "";
                    }

                    // Redirect back to the home page
                    final String finalContext = context;
                    return CompletableFuture.supplyAsync(() -> redirect(finalContext + "/"),
                            myHttpExecutionContext.current());
                }
                else {
                    // Render the not authenticated page
                    return CompletableFuture.supplyAsync(() -> ok(notAuthenticated.render(form.getEmail())),
                            myHttpExecutionContext.current());
                }
            }
            else {
                // The email and/or password does not match, so we add a new validation error.
                userForm.reject(new ValidationError("loginError", "Could not login."));

                // Render the page with the login form with the errors fields
                String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
                return CompletableFuture.supplyAsync(() -> badRequest(index.render(userForm, token)),
                        myHttpExecutionContext.current());
            }
        }
    }

    /**
     * <p>This handles the logout action for the Web IDE.</p>
     */
    public Result logout() {
        // Clear the session
        session().clear();

        // Obtain the http context from the configuration file
        String context = myConfiguration.getString("play.http.context");
        if (context == null) {
            context = "";
        }

        return redirect(context + "/");
    }

    /**
     * <p>This sends another confirmation email if the </p>
     *
     * @param email The email to send another confirmation email to.
     */
    @Transactional
    public Result generateNewConfirmation(String email) {
        // Check to see if the email exists. If it does not return
        // a valid "User", then we display the error page.
        User user = User.findByEmail(email);
        if (user != null) {
            // Generate a new confirmation code and send out another email.
            user = User.setNotAuthenticated(email);
            myEmailGenerator.generateConfirmationEmail(user.firstName,
                    user.email, user.confirmationCode);

            // Render registration success page.
            return ok(registrationSuccess.render());
        }
        else {
            // Render the account error page
            return ok(accountError.render());
        }
    }

}