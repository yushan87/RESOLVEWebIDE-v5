package models;

/* Libraries */
import java.util.Date;
import javax.persistence.*;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * TODO: Add JavaDocs for this class.
 */
@Entity
@Table(name="users")
public class User {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Unique ID for each user.</p> */
    @Id
    @GeneratedValue
    public Long id;

    /** <p>User's Email and Login User Name</p> */
    @Constraints.Email
    public String email;

    /** <p>User's First Name</p> */
    @Constraints.Required
    public String firstName;

    /** <p>User's Last Name</p> */
    @Constraints.Required
    public String lastName;

    /** <p>User's Password (Hashed)</p> */
    @Constraints.Required
    public String password;

    /** <p>User Type</p> */
    public int userType;

    /** <p>Last Login Date</p> */
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date lastLogin;

    /** <p>Account Creation Date</p> */
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date createdOn;

    /** <p>User's Default Project</p> */
    public String currentProject;

    /** <p>Boolean flag to check if User
     * is authenticated or not.</p> */
    public Boolean authenticated;

    /** <p>Temporary code generated by the system
     * for authentication purposes.</p> */
    public String confirmationCode;

    // ===========================================================
    // Constructors
    // ===========================================================

    public User() {}

    public User(String userEmail, String userPassword, String userFirstName, String userLastName) {
        // User information
        email = userEmail;
        firstName = userFirstName;
        lastName = userLastName;
        password = Utilities.encryptPassword(userPassword);
        userType = 0;
        lastLogin = null;
        createdOn = new Date();
        //currentProject = Project.getDefault().name;
        currentProject = "Default_Project";
        authenticated = false;

        // Generate confirmation code
        confirmationCode = Utilities.generateConfirmationCode(password,email,firstName,lastName);

        // Send email to the new user
        //Mails.confirmation(this);
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Set the specified user to be authenticated.</p>
     *
     * @param email User email.
     */
    public static void authenticate(String email) {
        User u = findByEmail(email);
        u.authenticated = true;
        u.confirmationCode = "";
        u.save();
    }

    /**
     * <p>Attempts to return a user if the email exists in
     * our database and the specified password matches as well.</p>
     *
     * @param email  Email entered by the user.
     * @param password Password entered by the user.
     *
     * @return User if all the information matches, null otherwise.
     */
    public static User connect(String email, String password) {
        User u = findByEmail(email);

        // Check user password if email is found.
        if (u != null) {
            if (!u.password.equals(Utilities.encryptPassword(password))) {
                u = null;
            }
        }

        return u;
    }

    /**
     * <p>Delete this user.</p>
     */
    public void delete() {
        JPA.em().remove(this);
    }

    /**
     * <p>Find a user by email.</p>
     *
     * @param email User email.
     *
     * @return The user corresponding to the query email.
     */
    public static User findByEmail(String email) {
        Query query = JPA.em().createQuery("select u from User u where u.email = :email", User.class);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    /**
     * <p>Checks to see if the user has been authenticated
     * or not.</p>
     *
     * @param email User email.
     *
     * @return True if user is authenticated, false otherwise.
     */
    public static Boolean hasAuthenticated(String email) {
        User u = findByEmail(email);
        return u.authenticated;
    }

    /**
     * <p>Update the last login date.</p>
     *
     * @param email User email.
     */
    public static void lastLogin(String email) {
        User u = findByEmail(email);
        u.lastLogin = new Date();
        u.save();
    }

    /**
     * <p>Store this user information.</p>
     */
    public void save() {
        JPA.em().persist(this);
    }

    /**
     * <p>Generate a new confirmation code and set the user
     * to not authenticated.</p>
     *
     * @param email Email entered by the user.
     */
    public static void setNotAuthenticated(String email) {
        User u = findByEmail(email);
        u.confirmationCode = Utilities.generateConfirmationCode(u.password, u.email, u.firstName, u.lastName);
        u.authenticated = false;
        u.save();
    }

    /**
     * <p>Update the password for the specified email.</p>
     *
     * @param email User email.
     * @param password User's new password.
     */
    public static void updatePassword(String email, String password) {
        User u = findByEmail(email);
        u.password = Utilities.encryptPassword(password);
        u.save();
    }
}