package deadbolt2.common.models;

import be.objectify.deadbolt.java.models.Permission;

/**
 * <p>An implementation of {@link Permission} for all users that will be used
 * by {@code Deadbolt2}.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public enum UserPermission implements Permission {

    // ===========================================================
    // Different User Permissions
    // ===========================================================

    /**
     * <p>This is a regular and authenticated user.</p>
     */
    ACTIVEUSER("An user whose account has been activated.");

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>A short description of the permission.</p> */
    public final String description;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>An helper constructor that creates a permission specific to
     * the WebIDE.</p>
     *
     * @param description A short description of the role.
     */
    UserPermission(String description) {
        this.description = description;
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Get the value of the permission.</p>
     *
     * @return a non-null String representation of the permission.
     */
    @Override
    public final String getValue() {
        return name();
    }

}