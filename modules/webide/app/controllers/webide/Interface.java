/**
 * ---------------------------------
 * Copyright (c) 2016
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package controllers.webide;

import java.util.List;
import javax.inject.Inject;
import models.common.database.Project;
import models.common.database.User;
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
    private CachedProjectNames myCachedObjects;

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
    @Transactional(readOnly = true)
    public Result index(int selectedProject) {
        // Retrieve the list of projects in the database
        List<Project> projectList = myCachedObjects.getProjects();
        Project activeProject;
        if (selectedProject == 0) {
            activeProject = myCachedObjects.getDefaultProject();
        }
        else {
            activeProject = projectList.get(selectedProject-1);
        }

        // Retrieve the current user (if logged in)
        String email = session("connected");
        User currentUser = null;
        if(email != null) {
            currentUser = User.findByEmail(email);
        }

        return ok(index.render(projectList, activeProject, currentUser));
    }
}