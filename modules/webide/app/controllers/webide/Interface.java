package controllers.webide;

import controllers.common.utilities.CachedObjects;
import java.util.List;
import javax.inject.Inject;
import controllers.common.email.EmailGenerator;
import models.common.database.Project;
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
     * @param selectedProject The project selected by the user.
     *
     * @return The result of rendering the page
     */
    @Transactional
    public Result index(int selectedProject) {
        List<Project> projectList = myCachedObjects.getProjects();
        Project activeProject;
        if (selectedProject == 0) {
            activeProject = myCachedObjects.getDefaultProject();
        }
        else {
            activeProject = projectList.get(selectedProject-1);
        }

        return ok(index.render(projectList, activeProject));
    }
}