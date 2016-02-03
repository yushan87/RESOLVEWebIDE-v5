package controllers.common.authentication;

import controllers.common.utilities.CachedObjects;
import javax.inject.Inject;
import java.util.List;
import models.common.database.Project;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.authentication.registration;

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
     * @param selectedProject The project selected by the user.
     *
     * @return The result of rendering the page
     */
    @Transactional(readOnly = true)
    public Result index(int selectedProject) {
        List<Project> projectList = myCachedObjects.getProjects();
        Project activeProject;
        if (selectedProject == 0) {
            activeProject = myCachedObjects.getDefaultProject();
        }
        else {
            activeProject = projectList.get(selectedProject-1);
        }

        return ok(registration.render(projectList, activeProject));
    }
}