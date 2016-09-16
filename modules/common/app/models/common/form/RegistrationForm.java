package models.common.form;

import models.common.ModelUtilities;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class serves as a model class for registration form and allows
 * the fields to be populated by Play Framework's {@link play.data.Form} class.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class RegistrationForm {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>New user's first name</p> */
    @Constraints.Required
    private String myFirstName;

    /** <p>New user's last name</p> */
    @Constraints.Required
    private String myLastName;

    /** <p>New user's email</p> */
    @Constraints.Email
    private String myEmail;

    /** <p>New user's password (encrypted)</p> */
    @Constraints.Required
    private String myPassword;

    /** <p>New user's retype of password (encrypted)</p> */
    @Constraints.Required
    private String myRetypePassword;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>First name field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getFirstName() {
        return myFirstName;
    }

    /**
     * <p>Stores the first name field.</p>
     *
     * @param firstName First name.
     */
    public final void setFirstName(String firstName) {
        myFirstName = firstName;
    }

    /**
     * <p>Last name field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getLastName() {
        return myLastName;
    }

    /**
     * <p>Stores the last name field.</p>
     *
     * @param lastName Last name.
     */
    public final void setLastName(String lastName) {
        myLastName = lastName;
    }

    /**
     * <p>Email field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getEmail() {
        return myEmail;
    }

    /**
     * <p>Stores the email field.</p>
     *
     * @param email Email
     */
    public final void setEmail(String email) {
        myEmail = email;
    }

    /**
     * <p>Password field in the registration form. Note that
     * we automatically encrypt the password.</p>
     *
     * @return A string.
     */
    public final String getPassword() {
        return myPassword;
    }

    /**
     * <p>Stores the password field.</p>
     *
     * @param password Password
     */
    public void setPassword(String password) {
        myPassword = ModelUtilities.encryptPassword(password);
    }

    /**
     * <p>Retype password field in the registration form. Note that
     * we automatically encrypt the password.</p>
     *
     * @return A string.
     */
    public final String getRetypePassword() {
        return myRetypePassword;
    }

    /**
     * <p>Stores the retype password field.</p>
     *
     * @param retypePassword Retyped password
     */
    public final void setRetypePassword(String retypePassword) {
        myRetypePassword = ModelUtilities.encryptPassword(retypePassword);
    }

    /**
     * <p>Validation method for the form.</p>
     *
     * @return A list of {@link ValidationError} if there are errors
     * in the registration form, {@code null} otherwise.
     */
    public final List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        return errors.isEmpty() ? null : errors;
    }

}