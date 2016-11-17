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

    /** <p>Timeout flag</p> */
    @Constraints.Required
    public int timeout;

    /** <p>Number of tries flag</p> */
    @Constraints.Required
    public int numTries;

    // ===========================================================
    // Constructor
    // ===========================================================

    /**
     * <p>Default constructor.</p>
     */
    public UpdateProfileForm() {}

    /**
     * <p>This generates a new form with the specified values.</p>
     *
     * @param firstName User first name
     * @param lastName User last name
     * @param email User email
     * @param timeout Timeout value
     * @param numTries Number of tries value
     */
    public UpdateProfileForm(String firstName, String lastName, String email,
                             int timeout, int numTries) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeout = timeout;
        this.numTries = numTries;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>First name field in the user profile update form.</p>
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
     * <p>Last name field in the user profile update form.</p>
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
     * <p>Email field in the user profile update form.</p>
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
     * <p>Timeout field in the user profile update form./p>
     *
     * @return An integer value.
     */
    public final int getTimeout() {
        return timeout;
    }

    /**
     * <p>Stores the timeout field.</p>
     *
     * @param timeout Timeout for the Prover
     */
    public final void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * <p>Number of tries field in the user profile update form./p>
     *
     * @return An integer value.
     */
    public final int getNumTries() {
        return numTries;
    }

    /**
     * <p>Stores the number of tries field.</p>
     *
     * @param numTries Number of tries for the Prover
     */
    public final void setNumTries(int numTries) {
        this.numTries = numTries;
    }

    /**
     * <p>This method overrides the default equals method implementation.</p>
     *
     * @param o Object to be compared.
     *
     * @return {@code true} if all the fields are equal, {@code false} otherwise.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateProfileForm that = (UpdateProfileForm) o;

        if (timeout != that.timeout) return false;
        if (numTries != that.numTries) return false;
        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;

        return email.equals(that.email);
    }

    /**
     * <p>This method overrides the default {@code hashCode} method implementation.</p>
     *
     * @return The hash code associated with the object.
     */
    @Override
    public final int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + timeout;
        result = 31 * result + numTries;

        return result;
    }

}