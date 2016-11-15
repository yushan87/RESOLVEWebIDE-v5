package controllers.common.profile;

import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import models.common.database.User;
import models.common.form.UpdateProfileForm;
import play.data.Form;
import play.data.FormFactory;
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

    /** <p>Form factory</p> */
    @Inject
    private FormFactory myFormFactory;

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
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if (email != null) {
            // Check to see if it is a valid user.
            User currentUser = User.findByEmail(email);
            if (currentUser != null) {
                String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
                Form<UpdateProfileForm> userForm = myFormFactory.form(UpdateProfileForm.class);
                userForm = userForm.fill(new UpdateProfileForm(currentUser.firstName, currentUser. lastName,
                        currentUser.email, "", currentUser.timeout, currentUser.numTries));

                return ok(editProfile.render(currentUser, userForm, token));
            }
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
        return null;
    }

}