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

package deadbolt2.common.models;

import be.objectify.deadbolt.java.models.Role;

/**
 * <p>An implementation of {@link Role} for all users that will be used
 * by {@code Deadbolt2}.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public enum UserRole implements Role {

    // ===========================================================
    // Different User Roles
    // ===========================================================

    /**
     * <p>This is a regular and authenticated user.</p>
     */
    USER(0, "A registered user."),

    /**
     * <p>A role given to researchers and educators that gives
     * them more permissions than regular users.</p>
     */
    SUPERUSER(1, "A researcher or educator that is using the WebIDE."),

    /**
     * <p>An administrative user that can perform all kinds of
     * database maintenance.</p>
     */
    ADMIN(2, "An administrative user that maintains the WebIDE.");

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>This is the value stored in the database.</p> */
    public final int dbRepresentation;

    /** <p>A short description of the role.</p> */
    public final String description;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>An helper constructor that creates a role specific to
     * the WebIDE.</p>
     *
     * @param dbRepresentation The integer representation stored in
     *                         the database.
     * @param description A short description of the role.
     */
    UserRole(int dbRepresentation, String description) {
        this.dbRepresentation = dbRepresentation;
        this.description = description;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Get the name of the role.</p>
     *
     * @return the non-null name of the role.
     */
    @Override
    public final String getName() {
        return name();
    }

}