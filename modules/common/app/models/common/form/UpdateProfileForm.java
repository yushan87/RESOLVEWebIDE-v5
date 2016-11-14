package models.common.form;

import play.data.validation.Constraints;

/**
 * <p>This class serves as a model class for update user profile form and allows
 * the fields to be populated by Play Framework's {@link play.data.Form} class.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class UpdateProfileForm {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>User's First Name</p> */
    @Constraints.Required
    public String firstName;

    /** <p>User's Last Name</p> */
    @Constraints.Required
    public String lastName;

    /** <p>User's Email and Login User Name</p> */
    @Constraints.Required
    @Constraints.Email
    public String email;

    /** <p>User's Password (Hashed)</p> */
    @Constraints.Required
    public String password;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>First name field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * <p>Stores the first name field.</p>
     *
     * @param firstName First name.
     */
    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * <p>Last name field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * <p>Stores the last name field.</p>
     *
     * @param lastName Last name.
     */
    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * <p>Email field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getEmail() {
        return email;
    }

    /**
     * <p>Stores the email field.</p>
     *
     * @param email Email
     */
    public final void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>Password field in the registration form./p>
     *
     * @return A string.
     */
    public final String getPassword() {
        return password;
    }

    /**
     * <p>Stores the password field.</p>
     *
     * @param password Password
     */
    public final void setPassword(String password) {
        this.password = password;
    }

}