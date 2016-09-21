package models.common.database;

import java.util.Date;
import javax.persistence.*;
import controllers.common.utilities.ControllerUtilities;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * <p>This class is the relational mapping of a user component in the database and provides
 * methods to change the user component in the database.</p>
 *
 * @author Chuck Cook
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Entity
@Table(name="userComponents")
public class UserComponent {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each user component.</p> */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /** <p>User component content.</p> */
    @Lob
    public String content;

    /** <p>User component name.</p> */
    public String name;

    /** <p>User component package.</p> */
    public String pkg;

    /** <p>User component project.</p> */
    public String project;

    /** <p>User component type.</p> */
    @Constraints.Required
    public String type;

    /** <p>User component author.</p> */
    @ManyToOne
    public User author;

    /** <p>User component creation date.</p> */
    @Column(name = "createdAt", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    /** <p>User component parent.</p> */
    public String parent;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>Default constructor. JPA needs this on some occasions.</p>
     */
    private UserComponent() {}

    /**
     * <p>Creates a new user component object.</p>
     *
     * @param ucName User component filename.
     * @param ucAuthor User component's author.
     * @param ucPkg User component's fake package hierarchy.
     * @param ucProject The project where this user component reside.
     * @param ucContent User component's content.
     * @param ucType The type of file for this user component.
     */
    private UserComponent(String ucName, String ucAuthor, String ucPkg, String ucProject, String ucContent, String ucType) {
        name = ucName;
        pkg = ucPkg;
        project = ucProject;
        content = ucContent;
        type = ucType;
        author = null;
        createdAt = new Date();
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Add a new user component to the database.</p>
     *
     * @param ucName User component filename.
     * @param ucAuthor User component's author.
     * @param ucPkg User component's fake package hierarchy.
     * @param ucProject The project where this user component reside.
     * @param ucContent User component's content.
     * @param ucType The type of file for this user component.
     *
     * @return The newly created user component object.
     */
    @Transactional
    public static UserComponent addUserComponent(String ucName, String ucAuthor, String ucPkg, String ucProject, String ucContent, String ucType) {
        UserComponent uc = new UserComponent(ucName, ucAuthor, ucPkg, ucProject, ucContent, ucType);
        uc.save();

        return uc;
    }

    /**
     * <p>This method converts the content into a formatted JSON string.
     * The format of this is dictated by the RESOLVE compiler's WebAPI.</p>
     *
     * @return A formatted JSON string that contains all the relevant
     * information about this user component.
     */
    public String toJson(){
        StringBuilder json = new StringBuilder();

        // Content
        json.append("{");
        json.append("\"name\":\"");
        json.append(name);
        json.append("\",");
        json.append("\"pkg\":\"");
        json.append(pkg);
        json.append("\",");
        json.append("\"project\":\"");
        json.append(project);
        json.append("\",");
        json.append("\"content\":\"");
        json.append(ControllerUtilities.encode(content));
        json.append("\",");
        json.append("\"parent\":\"");
        json.append(parent);
        json.append("\",");
        json.append("\"type\":\"");
        json.append(type);
        json.append("\"");
        json.append("}");

        return json.toString();
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Store this user component information.</p>
     */
    @Transactional
    private void save() {
        JPA.em().persist(this);
    }
}