package controllers.bydesign.core;

import models.common.database.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.bydesign.core.index;

/**
 * TODO: Write a description of this module
 */
public class Index extends Controller {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the byDesign page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    @Transactional(readOnly = true)
    public Result index() {
        // Retrieve the current user (if logged in)
        String email = session("connected");
        if(email != null) {
            User currentUser = User.findByEmail(email);

            return ok(index.render(currentUser));
        }

        return redirect(controllers.common.security.routes.Security.index());
    }

}