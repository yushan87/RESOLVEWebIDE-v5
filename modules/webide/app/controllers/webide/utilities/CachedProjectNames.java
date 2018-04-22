/*
 * ---------------------------------
 * Copyright (c) 2018
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package controllers.webide.utilities;

import models.common.database.Project;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.cache.SyncCacheApi;
import play.db.jpa.Transactional;

/**
 * <p>This class contains all the common objects used by the this package and
 * are stored in the cache and provides methods to retrieve them.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Singleton
public class CachedProjectNames {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Cache to store session values</p> */
    @Inject
    private SyncCacheApi myCache;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Retrieves the current default project for the current user.</p>
     *
     * @return The default {@link Project}.
     */
    @Transactional(readOnly = true)
    public Project getDefaultProject() {
        return myCache.getOrElseUpdate("defaultProject", this::storeDefaultProjectInCache);
    }

    /**
     * <p>Retrieves the list of projects available to the current user.</p>
     *
     * @return The list of {@link Project}s.
     */
    @Transactional(readOnly = true)
    public List<Project> getProjects() {
        return myCache.getOrElseUpdate("projects", this::storeProjectsInCache);
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Helper method to search for the default project,
     * put it in the cache and returns the project.</p>
     *
     * @return The default {@link Project}.
     */
    @Transactional(readOnly = true)
    private Project storeDefaultProjectInCache() {
        // Cache for 15 minutes
        Project defaultProject = Project.getDefault();
        myCache.set("defaultProject", defaultProject, 60 * 15);

        return defaultProject;
    }

    /**
     * <p>Helper method to search for the list of projects,
     * put it in the cache and returns the list of projects.</p>
     *
     * @return The list of {@link Project}s.
     */
    @Transactional(readOnly = true)
    private List<Project> storeProjectsInCache() {
        // Cache for 15 minutes
        List<Project> projects = Project.getOpenProjects();
        myCache.set("projects", projects, 60 * 15);

        return projects;
    }
}