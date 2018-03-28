/*
 * ---------------------------------
 * Copyright (c) 2018
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package models.common.form;

import play.data.validation.Constraints;

/**
 * <p>This class serves as a model class for update password form and allows
 * the fields to be populated by Play Framework's {@link play.data.Form} class.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class UpdatePasswordForm {

    // ===========================================================
    // Global Variables
    // ===========================================================

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