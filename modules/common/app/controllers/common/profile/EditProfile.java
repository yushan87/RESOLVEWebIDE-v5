package controllers.common.profile;

import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.mvc.Controller;
import play.mvc.Result;

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
        //String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
        //return ok(registration.render(myFormFactory.form(RegistrationForm.class), token));
        return null;
    }

}