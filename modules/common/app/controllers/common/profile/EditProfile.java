package controllers.common.profile;

import controllers.common.email.EmailGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
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
import play.libs.concurrent.HttpExecutionContext;
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

    /** <p>An executor to get the current HTTP context.</p> */
    @Inject
    private HttpExecutionContext myHttpExecutionContext;

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
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
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
    public CompletionStage<Result> handleSubmit() {
        // Check to see if it is a valid user and is connected.
        String connectedUserEmail = session("connected");
        User currentUser = getUser(connectedUserEmail);
        if (currentUser != null) {
            Form<UpdateProfileForm> userForm = myFormFactory.form(UpdateProfileForm.class).bindFromRequest();

            // Perform the basic validation checks.
            if (userForm.hasErrors()) {
                String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
                return CompletableFuture.supplyAsync(() -> badRequest(editProfile.render(currentUser, userForm, token, false)),
                        myHttpExecutionContext.current());
            } else {
                UpdateProfileForm form = userForm.get();

                // Perform our own validation checks. If we detect errors, then
                // we display the registration page with the errors highlighted.
                // If there are no errors, we display the success page.
                CompletionStage<List<ValidationError>> resultPromise = validate(connectedUserEmail, form);
                return resultPromise.thenApplyAsync(result -> {
                    String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
                    if (result != null) {
                        for (ValidationError error : result) {
                            userForm.reject(error);
                        }
                        return badRequest(editProfile.render(currentUser, userForm, token, false));
                    } else {

                        // Edit the user entry in the database.
                        // Note 1: "editUserProfile" expects a JPA entity manager,
                        // which is not present if we don't wrap the call using
                        // "withTransaction()".
                        // Note 2: It is possible that that this will fail if we fail to
                        // retrieve data from the database. We are ignoring this for now.
                        myJpaApi.withTransaction(() -> User.editUserProfile(connectedUserEmail,
                                form.getFirstName(), form.getLastName(), form.getEmail(),
                                form.getTimeout(), form.getNumTries()));
                        final User updatedUser = myJpaApi.withTransaction(() -> getUser(form.getEmail()));

                        Form<UpdateProfileForm> updatedForm = myFormFactory.form(UpdateProfileForm.class);
                        updatedForm = updatedForm.fill(new UpdateProfileForm(updatedUser.firstName, updatedUser.lastName,
                                updatedUser.email, updatedUser.timeout, updatedUser.numTries));

                        // Update the session
                        if (!connectedUserEmail.equals(updatedUser.email)) {
                            myEmailGenerator.generateUpdateAccountEmail(updatedUser.firstName,
                                    connectedUserEmail, updatedUser.email);
                            session("connected", updatedUser.email);
                        }

                        return ok(editProfile.render(updatedUser, updatedForm, token, true));
                    }
                }, myHttpExecutionContext.current());
            }
        }

        return CompletableFuture.supplyAsync(() -> redirect(controllers.common.security.routes.Security.index()),
                myHttpExecutionContext.current());
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
     * @return A {@link CompletionStage} with a list of {@link ValidationError}
     * if there are errors in the user profile form, {@code null} otherwise.
     */
    @Transactional(readOnly = true)
    private CompletionStage<List<ValidationError>> validate(String connectedUserEmail, UpdateProfileForm form) {
        return CompletableFuture.supplyAsync(() -> {
            List<ValidationError> errors = new ArrayList<>();

            // Check for a registered user with the same email.
            if (!connectedUserEmail.equals(form.getEmail()) && getUser(form.getEmail()) != null) {
                errors.add(new ValidationError("registeredEmail", "This e-mail is already in use by another user."));
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
        }, myHttpExecutionContext.current());
    }

}