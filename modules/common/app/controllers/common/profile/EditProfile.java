/*
 * ---------------------------------
 * Copyright (c) 2018
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package controllers.common.profile;

import controllers.common.email.EmailGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import models.common.database.User;
import models.common.form.UpdateProfileForm;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.profile.editProfile;

/**
 * <p>This class serves as a controller class for editing your
 * user profile.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class EditProfile extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Email generator</p> */
    @Inject
    private EmailGenerator myEmailGenerator;

    /** <p>Form factory</p> */
    @Inject
    private FormFactory myFormFactory;

    /** <p>JPA API</p> */
    @Inject
    private JPAApi myJpaApi;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the page to edit your user profile.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @Transactional(readOnly = true)
    public Result index() {
        // Check to see if it is a valid user and is connected.
        User currentUser = getUser(session("connected"));
        if (currentUser != null) {
            String token = CSRF.getToken(request()).map(CSRF.Token::value).orElse("no token");
            Form<UpdateProfileForm> userForm = myFormFactory.form(UpdateProfileForm.class);
            userForm = userForm.fill(new UpdateProfileForm(currentUser.firstName, currentUser.lastName,
                    currentUser.email, currentUser.timeout, currentUser.numTries));

            return ok(editProfile.render(currentUser, userForm, token, false));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

    /**
     * <p>This handles the {@link UpdateProfileForm} submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional
    public Result handleSubmit() {
        // Check to see if it is a valid user and is connected.
        String connectedUserEmail = session("connected");
        String token = CSRF.getToken(request()).map(CSRF.Token::value).orElse("no token");
        User currentUser = getUser(connectedUserEmail);
        if (currentUser != null) {
            Form<UpdateProfileForm> userForm = myFormFactory.form(UpdateProfileForm.class).bindFromRequest();

            // Perform the basic validation checks.
            if (userForm.hasErrors()) {
                return badRequest(editProfile.render(currentUser, userForm, token, false));
            } else {
                UpdateProfileForm form = userForm.get();

                // Perform our own validation checks. If we detect errors, then
                // we display the registration page with the errors highlighted.
                // If there are no errors, we display the success page.
                List<ValidationError> result = validate(connectedUserEmail, form);
                if (result != null) {
                    for (ValidationError error : result) {
                        userForm.reject(error);
                    }
                    return badRequest(editProfile.render(currentUser, userForm, token, false));
                } else {
                    // Check to see if there is a change
                    Form<UpdateProfileForm> currentUserForm = myFormFactory.form(UpdateProfileForm.class);
                    currentUserForm = currentUserForm.fill(new UpdateProfileForm(currentUser.firstName,
                            currentUser.lastName, currentUser.email, currentUser.timeout, currentUser.numTries));
                    UpdateProfileForm cuForm = currentUserForm.get();
                    if (form.equals(cuForm)) {
                        return ok(editProfile.render(currentUser, userForm, token, false));
                    }
                    else {
                        // Store the email addresses for future use.
                        // Note: This is necessary because database changes changes the values of
                        // currentUser and updatedUser.
                        String oldEmail = cuForm.email;
                        String newEmail = form.email;

                        // Edit the user entry in the database.
                        // Note: It is possible that that this will fail if we fail to
                        // retrieve data from the database. We are ignoring this for now.
                        User.editUserProfile(oldEmail, form.getFirstName(), form.getLastName(), form.getEmail(),
                                form.getTimeout(), form.getNumTries());
                        final User updatedUser = getUser(form.getEmail());

                        // Update the session
                        if (!oldEmail.equals(newEmail)) {
                            myEmailGenerator.generateUpdateAccountEmail(updatedUser.firstName, oldEmail, newEmail);
                            session("connected", newEmail);
                        }

                        return ok(editProfile.render(updatedUser, userForm, token, true));
                    }
                }
            }
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>An helper method to retrieve the {@link User} associated
     * with the provided email.</p>
     *
     * @param email A valid email address.
     *
     * @return A {@link User} if it is a valid user, {@code null} otherwise.
     */
    @Transactional(readOnly = true)
    private User getUser(String email) {
        User user = null;
        if (email != null) {
            user = User.findByEmail(email);
        }

        return user;
    }

    /**
     * <p>Our own custom validation method for the user profile form.</p>
     *
     * @param connectedUserEmail The current connected user's email.
     * @param form The current user profile form we are processing.
     *
     * @return A list of {@link ValidationError} if there are errors in the user profile form,
     * {@code null} otherwise.
     */
    @Transactional(readOnly = true)
    private List<ValidationError> validate(String connectedUserEmail, UpdateProfileForm form) {
        List<ValidationError> errors = new ArrayList<>();

        // Check for a registered user with the same email.
        if (!connectedUserEmail.equals(form.getEmail())) {
            final User otherUser = myJpaApi.withTransaction(() -> getUser(form.getEmail()));
            if (otherUser != null) {
                errors.add(new ValidationError("registeredEmail", "This e-mail is already in use by another user."));
            }
        }

        // Check that we have a valid prover timeout
        if (form.getTimeout() < 1 || form.getTimeout() > 30) {
            errors.add(new ValidationError("timeoutSize", "Must be a number between 1-30 (time in seconds)."));
        }

        // Check that we have a valid number of tries before quiting.
        if (form.getNumTries() < 1 || form.getTimeout() > 10) {
            errors.add(new ValidationError("numTriesSize", "Must be a number between 1-10."));
        }

        return errors.isEmpty() ? null : errors;
    }
}