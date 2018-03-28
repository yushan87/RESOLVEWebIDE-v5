/*
 *  ---------------------------------
 *  Copyright (c) 2017
 *  RESOLVE Software Research Group
 *  School of Computing
 *  Clemson University
 *  All rights reserved.
 *  ---------------------------------
 *  This file is subject to the terms and conditions defined in
 *  file 'LICENSE.txt', which is part of this source code package.
 */

package models.common.form;

import play.data.validation.Constraints;

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

    /** <p>reCaptcha response value</p> */
    @Constraints.Required
    private String reCaptcha;

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

    /**
     * <p>Confirm password field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * <p>Stores the confirm password field.</p>
     *
     * @param confirmPassword Retyped password
     */
    public final void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * <p>reCaptcha field in the registration form.</p>
     *
     * @return A string.
     */
    public final String getReCaptcha() {
        return reCaptcha;
    }

    /**
     * <p>Stores the reCaptcha field.</p>
     *
     * @param reCaptcha reCaptcha
     */
    public final void setReCaptcha(String reCaptcha) {
        this.reCaptcha = reCaptcha;
    }

}