package models.common.form;

import play.data.validation.Constraints;

/**
 * <p>This class serves as a model class for login form and allows
 * the fields to be populated by Play Framework's {@link play.data.Form} class.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class LoginForm {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>New user's email</p> */
    @Constraints.Required
    @Constraints.Email
    private String email;

    /** <p>New user's password</p> */
    @Constraints.Required
    private String password;

    // ===========================================================
    // Public Methods
    // ===========================================================

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