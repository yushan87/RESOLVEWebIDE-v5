package controllers.common.dbmanagers;

/* Libraries */
import java.util.Date;
import controllers.common.HibernateUtil;
import models.common.database.User;
import models.common.Utilities;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * TODO: Add JavaDocs for this class.
 */
public class UserManager {

    // Singleton class that returns a UserManager that handles all
    // interactions with the "users" table.
    private static UserManager myUserManager;

    static {
        myUserManager = new UserManager();
    }

    // ===========================================================
    // Constructors
    // ===========================================================

    private UserManager() {}

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Set the specified user to be authenticated.</p>
     *
     * @param u The <code>User</code> we wish to authenticate.
     */
    public void authenticate(User u) {
        // Obtain the current session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Change authenticated to true and empty the confirmation code.
        u.authenticated = true;
        u.confirmationCode = "";

        // Store the update to the session
        session.update(u);
        session.getTransaction().commit();
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
    public User connect(String email, String password) {
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
     * <p>Delete the specified user.</p>
     *
     * @param u The <code>User</code> we wish to delete.
     */
    public void delete(User u) {
        // Obtain the current session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Delete the user
        session.delete(u);

        // Store the update to the session
        session.getTransaction().commit();
    }

    /**
     * <p>Find a user by email.</p>
     *
     * @param email User email.
     *
     * @return The <code>User</code> corresponding to the query email.
     */
    public static User findByEmail(String email) {
        // Obtain the current session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Query and locate a user with the specified email
        Query query = session.createQuery("select u from User u where u.email = :email");
        query.setParameter("email", email);
        User u = (User) query.uniqueResult();

        // Store the update to the session
        session.getTransaction().commit();

        return u;
    }

    /**
     * <p>Returns the <code>UserManager</code> singleton instance.</p>
     *
     * @return The current <code>UserManager</code>.
     */
    public static UserManager getInstance() {
        return myUserManager;
    }

    /**
     * <p>Update the last login date.</p>
     *
     * @param u The <code>User</code> we wish to update.
     */
    public void lastLogin(User u) {
        // Obtain the current session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Update the login date
        u.lastLogin = new Date();

        // Store the update to the session
        session.update(u);
        session.getTransaction().commit();
    }

    /**
     * <p>Generate a new confirmation code and set the user
     * to not authenticated.</p>
     *
     * @param u The <code>User</code> we wish to update.
     */
    public void setNotAuthenticated(User u) {
        // Obtain the current session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Generate a new confirmation code and change to not authenticated
        u.confirmationCode = Utilities.generateConfirmationCode(u.password, u.email, u.firstName, u.lastName);
        u.authenticated = false;

        // Store the update to the session
        session.update(u);
        session.getTransaction().commit();
    }

    /**
     * <p>Update the password for the specified <code>User</code>.</p>
     *
     * @param u The <code>User</code> we wish to update.
     * @param password User's new password.
     */
    public void updatePassword(User u, String password) {
        // Obtain the current session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Encrypt the new password and replace it on the database
        u.password = Utilities.encryptPassword(password);

        // Store the update to the session
        session.update(u);
        session.getTransaction().commit();
    }
}
