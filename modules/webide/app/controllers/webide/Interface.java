package controllers.webide;

import controllers.common.CachedObjects;
import javax.inject.Inject;
import controllers.common.EmailGenerator;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.webide.index;

/**
 * TODO: Write a description of this module
 */
public class Interface extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Collection of cached objects</p> */
    @Inject
    private CachedObjects myCachedObjects;

    /** <p>An email generator.</p> */
    @Inject
    private EmailGenerator myEmailGenerator;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    @Transactional
    public Result index() {
        return ok(index.render(myCachedObjects.getProjects(), myCachedObjects.getDefaultProject()));
    }
}