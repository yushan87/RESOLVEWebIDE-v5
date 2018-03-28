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
 * <p>This class serves as a model class for password recovery form and allows
 * the fields to be populated by Play Framework's {@link play.data.Form} class.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class PasswordRecoveryForm {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>New user's email</p> */
    @Constraints.Required
    @Constraints.Email
    private String email;

    /** <p>reCaptcha response value</p> */
    @Constraints.Required
    private String reCaptcha;

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