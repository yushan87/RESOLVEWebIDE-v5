package models.common.form;

import java.util.ArrayList;
import java.util.List;
import models.common.database.User;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

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
    private String firstName;

    /** <p>New user's last name</p> */
    @Constraints.Required
    private String lastName;

    /** <p>New user's email</p> */
    @Constraints.Required
    @Constraints.Email
    private String email;

    /** <p>New user's password</p> */
    @Constraints.Required
    private String password;

    /** <p>New user's confirmation of password</p> */
    @Constraints.Required
    private String confirmPassword;

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
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Confirm password field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * <p>Stores the retype password field.</p>
     *
     * @param confirmPassword Retyped password
     */
    public final void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * <p>Validation method for the form.</p>
     *
     * @return A list of {@link ValidationError} if there are errors
     * in the registration form, {@code null} otherwise.
     */
    public final List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        // Check for a registered user with the same email.
        if (User.findByEmail(email) != null) {
            errors.add(new ValidationError("email", "This e-mail is already registered."));
        }

        // Check that the password has a minimum length of 6 and a maximum of 20
        if (password.length() < 6 || password.length() > 20) {
            errors.add(new ValidationError("passwordLength", "The password must be 6-20 characters long."));
        } else {
            if (!password.equals(confirmPassword)) {
                errors.add(new ValidationError("notSamePassword", "The two password fields do not match."));
            }
        }

        return errors.isEmpty() ? null : errors;
    }

}