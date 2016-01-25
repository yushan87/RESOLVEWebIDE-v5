package controllers.analytics;

import controllers.common.CachedObjects;
import javax.inject.Inject;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.analytics.index;

/**
 * TODO: Write a description of this module
 */
public class Analytics extends Controller {

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
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    @Transactional(readOnly = true)
    public Result index() {
        return ok(index.render(myCachedObjects.getProjects(), myCachedObjects.getDefaultProject()));
    }
}