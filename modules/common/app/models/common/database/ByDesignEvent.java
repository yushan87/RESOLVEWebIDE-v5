package models.common.database;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * <p>This class is the relational mapping of a user event in the database and provides
 * methods to change the {@code byDesign} events in the database.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Entity
@Table(name="byDesignEvents")
public class ByDesignEvent {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each user event.</p> */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /** <p>Author associated with this {@code byDesign} event.</p> */
    @ManyToOne
    @Constraints.Required
    public long author;

    /** <p>Code associated with this {@code byDesign} event.</p> */
    @Constraints.Required
    @Lob
    public String code;

    /**
     * <p>Boolean flag that indicates whether or not the code referred by this
     * {@code byDesign} event verified.</p>
     */
    @Constraints.Required
    public boolean correct;

    /** <p>Lesson name associated with this {@code byDesign} event.</p> */
    @Constraints.Required
    public String lesson;

    /** <p>Module name associated with this {@code byDesign} event.</p> */
    @Constraints.Required
    public String module;

    /** <p>Points associated with this {@code byDesign} event.</p> */
    @Constraints.Required
    public long points;

    /** <p>Time spent on the code associated with this {@code byDesign} event.</p> */
    @Constraints.Required
    public long time;

    /** <p>Date associated with this {@code byDesign} event.</p> */
    @Constraints.Required
    @Column(name = "eventDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date eventDate;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>Default constructor. JPA needs this on some occasions.</p>
     */
    private ByDesignEvent() {}

    /**
     * <p>Creates a compiler related {@code byDesign} event object.</p>
     *
     * @param bdAuthor The author's id number.
     * @param bdCode The code associated with this event.
     * @param bdCorrect A flag that indicates whether the author got this lesson
     *                  correctly or not.
     * @param bdLesson The lesson associated with this event.
     * @param bdModule The module associated with this event.
     * @param bdPoints The amount of points earned by the author.
     * @param bdTime The time spent on this lesson.
     */
    private ByDesignEvent(long bdAuthor, String bdCode, boolean bdCorrect, String bdLesson,
                          String bdModule, long bdPoints, long bdTime) {
        author = bdAuthor;
        code = bdCode;
        correct = bdCorrect;
        lesson = bdLesson;
        module = bdModule;
        points = bdPoints;
        time = bdTime;
        eventDate = new Date();
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Add a {@code byDesign} related event to the database.</p>
     *
     * @param bdAuthor The author's id number.
     * @param bdCode The code associated with this event.
     * @param bdCorrect A flag that indicates whether the author got this lesson
     *                  correctly or not.
     * @param bdLesson The lesson associated with this event.
     * @param bdModule The module associated with this event.
     * @param bdPoints The amount of points earned by the author.
     * @param bdTime The time spent on this lesson.
     *
     * @return The newly created {@code byDesign} event object.
     */
    @Transactional
    public static ByDesignEvent addByDesignEvent(long bdAuthor, String bdCode, boolean bdCorrect, String bdLesson,
                                                 String bdModule, long bdPoints, long bdTime) {
        ByDesignEvent bde = new ByDesignEvent(bdAuthor, bdCode, bdCorrect, bdLesson, bdModule, bdPoints, bdTime);
        bde.save();

        return bde;
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Store this {@code byDesign} event information.</p>
     */
    @Transactional
    private void save() {
        JPA.em().persist(this);
    }

}