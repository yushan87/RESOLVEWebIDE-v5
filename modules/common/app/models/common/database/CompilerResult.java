package models.common.database;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * <p>This class is the relational mapping of a compiler result in the database and provides
 * methods to change the compiler result in the database.</p>
 *
 * @author Caleb Priester
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Entity
@Table(name="compilerResults")
public class CompilerResult {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each compiler result.</p> */
    @Id
    @GeneratedValue
    public Long id;

    /** <p>Compiling file's content.</p> */
    @Constraints.Required
    @Lob
    public String content;

    /** <p>Indicator for any compilation errors.</p> */
    public int error;

    /** <p>Compiler Result Creation Date</p> */
    @Column(name = "createdOn", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @Constraints.Required
    public Date eventDate;

    /** <p>The compiler job being run.</p> */
    @Constraints.Required
    public String eventType;

    /** <p>Compiling file's file extension type.</p> */
    @Constraints.Required
    public String fileType;

    /** <p>Compiling file's name.</p> */
    @Constraints.Required
    public String name;

    /** <p>Compiling file's parent in the hierarchy.</p> */
    @Constraints.Required
    public String parent;

    /** <p>Compiling file's fake package hierarchy.</p> */
    @Constraints.Required
    public String pkg;

    /** <p>Compiling file's project information.</p> */
    @Constraints.Required
    public String project;

    /** <p>The compiler result information.</p> */
    @Constraints.Required
    @Lob
    public String results;

    /** <p>User event author.</p> */
    @ManyToOne
    @Constraints.Required
    public User author;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>Default constructor. JPA needs this on some occasions.</p>
     */
    private CompilerResult() {}

    /**
     * <p>Creates a new compiler result object.</p>
     *
     * @param name The compiling object's filename.
     * @param parent The compiling object's parent in the hierarchy.
     * @param pkg The compiling object's fake package hierarchy.
     * @param project The project where this component reside.
     * @param fileType The compiling object's file extension type.
     * @param content The compiling object's content.
     * @param eventType The event type description.
     * @param results The compiler results.
     * @param error The error information provided by the compiler (if any).
     * @param author The user that generated this event.
     */
    private CompilerResult(String name, String parent, String pkg, String project, String fileType, String content,
                          String eventType, String results, int error, User author){
        this.name = name;
        this.pkg = pkg;
        this.project = project;
        this.fileType = fileType;
        this.eventType = eventType;
        this.content = content;
        this.results = results;
        this.error = error;
        this.eventDate = new Date();
        this.author = author;
        this.parent = parent;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Add a compilation result to the database.</p>
     *
     * @param filename The compiling object's filename.
     * @param fileparent The compiling object's parent in the hierarchy.
     * @param pkg The compiling object's fake package hierarchy.
     * @param projectName The project where this component reside.
     * @param filetype The compiling object's file extension type.
     * @param filecontent The compiling object's content.
     * @param eventType The event type description.
     * @param results The compiler results.
     * @param error The error information provided by the compiler (if any).
     * @param author The user that generated this event.
     *
     * @return The newly created compiler result object.
     */
    @Transactional
    public static CompilerResult addCompilerResult(String filename, String fileparent, String pkg, String projectName, String filetype,
                                                   String filecontent, String eventType, String results, int error, User author) {
        CompilerResult cr = new CompilerResult(filename, fileparent, pkg, projectName, filetype, filecontent, eventType, results, error, author);
        cr.save();

        return cr;
    }

    /**
     * <p>Find compiler results by name.</p>
     *
     * @param name CompilerResult name.
     *
     * @return The CompilerResults corresponding to the query name.
     */
    @Transactional(readOnly = true)
    public static List<CompilerResult> findByName(String name) {
        Query query = JPA.em()
                .createQuery("select cr from CompilerResult cr where cr.name = :name", CompilerResult.class);
        query.setParameter("name", name);

        return query.getResultList();
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>Store this compiler result information.</p>
     */
    @Transactional
    private void save() {
        JPA.em().persist(this);
    }
}