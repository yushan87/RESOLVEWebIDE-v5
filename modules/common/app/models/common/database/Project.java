package models.common.database;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * <p>This class is the relational mapping of a project in the database and provides
 * methods to change the project in the database.</p>
 *
 * @author Chuck Cook
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Entity
@Table(name="projects")
public class Project {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each project.</p> */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /** <p>Name of Project</p> */
    @Constraints.Required
    public String name;

    /** <p>Owner of Project</p> */
    @Constraints.Email
    public String ownerEmail;

    /** <p>JSON Representation of the Project</p> */
    public String jsonRep;

    /** <p>Date that generated this JSON Representation</p> */
    public String jsonRepDate;

    /** <p>Check if this Project is open or not.</p> */
    public boolean openProject;

    /** <p>Sets the visibility of the standard concept
     * templates.</p> */
    public boolean standardHidden;

    /** <p>Check if this is the default project.</p> */
    public boolean defaultProject;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>Default constructor. JPA needs this on some occasions.</p>
     */
    private Project() {}

    /**
     * <p>Creates a new project object.</p>
     *
     * @param projName Project name
     * @param projOwnerEmail Project owner email
     * @param projOpenProject Boolean that indicates if this is an open project.
     * @param projStandardHidden Boolean that indicates if the standard facilities are hidden or not.
     */
    private Project(String projName, String projOwnerEmail, boolean projOpenProject, boolean projStandardHidden){
        name = projName;
        ownerEmail = projOwnerEmail;
        jsonRep = "";
        jsonRepDate = "";
        openProject = projOpenProject;
        standardHidden = projStandardHidden;
        defaultProject = false;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Add a new project to the database.</p>
     *
     * @param name Project name
     * @param email Project owner email
     * @param isOpenProject Boolean that indicates if this is an open project.
     * @param isStandardHidden Boolean that indicates if the standard facilities are hidden or not.
     *
     * @return The newly created project object.
     */
    @Transactional
    public static Project addProject(String name, String email, boolean isOpenProject, boolean isStandardHidden) {
        Project p = new Project(name, email, isOpenProject, isStandardHidden);
        p.save();

        return p;
    }

    /**
     * <p>Returns the default project.</p>
     *
     * @return Default project object.
     */
    @Transactional(readOnly = true)
    public static Project getDefault() {
        Query query = JPA.em().createQuery("select p from Project p where p.defaultProject = true", Project.class);

        List result = query.getResultList();
        Project defaultProject = null;
        if (!result.isEmpty()) {
            defaultProject = (Project) result.get(0);
        }

        return defaultProject;
    }

    /**
     * <p>Returns the list of all open projects.</p>
     *
     * @return List of all open project object.
     */
    @Transactional(readOnly = true)
    public static List<Project> getOpenProjects(){
        Query query = JPA.em().createQuery("from Project p where p.openProject = true", Project.class);
        List results = query.getResultList();

        return Lists.newArrayList(Iterables.filter(results, Project.class));
    }

    /**
     * <p>Returns the list of all private projects.</p>
     *
     * @return List of all private project objects.
     */
    @Transactional(readOnly = true)
    public static List<Project> getPrivateProjects() {
        Query query = JPA.em().createQuery("from Project p where p.email != null and p.openProject = false", Project.class);
        List results = query.getResultList();

        return Lists.newArrayList(Iterables.filter(results, Project.class));
    }

    /**
     * <p>Returns the project with the specified name and owner email.</p>
     *
     * @param name Project name
     * @param email Project owner email
     *
     * @return Project object.
     */
    @Transactional(readOnly = true)
    public static Project getProject(String name, String email) {
        Query query = JPA.em().createQuery("select p from Project p where p.name = :name and p.email = :email", Project.class);
        query.setParameter("name", name);
        query.setParameter("email", email);

        // Attempt to find the project
        List result = query.getResultList();
        Project p = null;
        if (!result.isEmpty()) {
            p = (Project) result.get(0);
        }

        return p;
    }

    /**
     * <p>List of projects owned by this email.</p>
     *
     * @param email Project owner email
     *
     * @return List of all projects owned by this email.
     */
    @Transactional(readOnly = true)
    public static List<Project> getUserProjects(String email) {
        Query query = JPA.em().createQuery("from Project p where p.email = :email and p.openProject = false", Project.class);
        query.setParameter("email", email);
        List results = query.getResultList();

        return Lists.newArrayList(Iterables.filter(results, Project.class));
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Delete this project.</p>
     */
    @Transactional
    private void delete() {
        JPA.em().remove(this);
    }

    /**
     * <p>Store this project information.</p>
     */
    @Transactional
    private void save() {
        JPA.em().persist(this);
    }
}