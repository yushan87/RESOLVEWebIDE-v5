package models.common.database;

import java.util.Date;
import javax.persistence.*;
import controllers.common.ControllerUtilities;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * TODO: Add JavaDocs for this class.
 */
@Entity
@Table(name="userComponents")
public class UserComponent {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each user component.</p> */
    @Id
    @GeneratedValue
    public Long id;

    /** <p>User component name.</p> */
    @Constraints.Required
    public String name;

    /** <p>User component package.</p> */
    @Constraints.Required
    public String pkg;

    /** <p>User component project.</p> */
    @Constraints.Required
    public String project;

    /** <p>User component type.</p> */
    @Constraints.Required
    public String type;

    /** <p>User component parent.</p> */
    public String parent;

    /** <p>User component creation date.</p> */
    @Column(name = "createdAt", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    /** <p>User component content.</p> */
    @Lob
    @Constraints.Required
    public String content;

    /** <p>User component author.</p> */
    @ManyToOne
    public User author;

    // ===========================================================
    // Constructors
    // ===========================================================

    public UserComponent(String ucName, String ucPkg, String ucProject, String ucContent, String ucType) {
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
}