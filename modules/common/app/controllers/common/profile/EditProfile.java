package controllers.common.profile;

import models.common.database.User;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
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

    /**
     * <p>This renders the page to edit your user profile.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    public Result index() {
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if (email != null) {
            // Check to see if it is a valid user.
            User currentUser = User.findByEmail(email);
            if (currentUser != null) {
                String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");

                return ok(editProfile.render(currentUser, token));
            }
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

}