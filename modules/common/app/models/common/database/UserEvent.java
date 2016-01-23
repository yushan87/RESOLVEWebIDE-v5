package models.common.database;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * TODO: Add JavaDocs for this class.
 */
@Entity
@Table(name="userEvents")
public class UserEvent {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>User event file name.</p> */
    @Constraints.Required
    public String name;

    /** <p>User event file package.</p> */
    @Constraints.Required
    public String pkg;

    /** <p>User event file project.</p> */
    @Constraints.Required
    public String project;

    /** <p>User event type.</p> */
    @Constraints.Required
    public String eventType;

    /** <p>User event content.</p> */
    @Constraints.Required
    public String content;

    /** <p>User event date.</p> */
    @Constraints.Required
    @Column(name = "eventDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date eventDate;

    /** <p>User event author.</p> */
    @ManyToOne
    @Constraints.Required
    public User author;

    // ===========================================================
    // Constructors
    // ===========================================================

    public UserEvent(String ueName, String uePkg, String ueProject, String ueEventType,
                     String ueContent, User ueAuthor) {
        name = ueName;
        pkg = uePkg;
        project = ueProject;
        eventType = ueEventType;
        content = ueContent;
        eventDate = new Date();
        author = ueAuthor;
    }

    public UserEvent(String ueEventType, String ueProject, User ueAuthor) {
        project = ueProject;
        eventType = ueEventType;
        eventDate = new Date();
        author = ueAuthor;
    }
}