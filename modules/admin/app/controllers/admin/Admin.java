package controllers.admin;

import models.common.database.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.index;

/**
 * TODO: Write a description of this module
 */
public class Admin extends Controller {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the admin page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    @Transactional(readOnly = true)
    public Result index() {
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if(email != null) {
            User currentUser = User.findByEmail(email);

            if (currentUser.userType != 2) {
                return unauthorized("You do not have permission to view this page!");
            }
            else {
                return ok(index.render(currentUser));
            }
        }

        return unauthorized("You do not have permission to view this page!");
    }

}