/*
 * ---------------------------------
 * Copyright (c) 2017
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package utils.bydesign.dataanalysis;

/**
 * <p>This file contains the various different errors we might encounter during
 * data analysis and it's corresponding string message to the user.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public enum ErrorKind {

    // ===========================================================
    // Different Error Codes
    // ===========================================================

    /**
     * <p>Error 1: Invalid Input File.</p>
     */
    INVALID_INPUT_FILE(1, "The input file is not a valid CSV file."),

    /**
     * <p>Error 3: Error Retrieving Data from the Database.</p>
     */
    DATABASE_ERROR(2,
            "An error occurred while retrieving data from the database.");

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Error code</p> */
    public final int code;

    /** <p>Message associated with the error</p> */
    public final String message;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>Helper constructor that allow us to create the various different
     * {@link ErrorKind}.</p>
     *
     * @param code Error code.
     * @param message Message associated with this error.
     */
    ErrorKind(int code, String message) {
        this.code = code;
        this.message = message;
    }

}