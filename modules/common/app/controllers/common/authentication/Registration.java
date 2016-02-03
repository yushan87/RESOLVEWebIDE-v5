package controllers.common.authentication;

import controllers.common.utilities.CachedObjects;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.authentication.registration;

import javax.inject.Inject;

/**
 * TODO: Add JavaDocs for this class.
 */
public class Registration extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Collection of cached objects</p> */
    @Inject
    private CachedObjects myCachedObjects;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the registration page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    @Transactional(readOnly = true)
    public Result index() {
        return ok(registration.render(myCachedObjects.getProjects(), myCachedObjects.getDefaultProject()));
    }
}