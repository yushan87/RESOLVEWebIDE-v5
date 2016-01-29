package controllers.common.securesocial;

import models.common.ModelUtilities;
import play.libs.Scala;
import securesocial.core.PasswordInfo;
import securesocial.core.providers.utils.PasswordHasher;

/**
 * TODO: Add JavaDocs for this class.
 */
public class CustomPasswordHasher extends PasswordHasher {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>The custom password hasher identifier.</p>
     *
     * @return The identifier string.
     */
    @Override
    public String id() {
        return "Password Hasher for RESOLVE WebIDE";
    }

    /**
     * <p>Hashes a password.</p>
     *
     * @param plainPassword The plain password to hash.
     *
     * @return A {@link PasswordInfo} containing the hashed password.
     */
    @Override
    public PasswordInfo hash(String plainPassword) {
        return new PasswordInfo("resolvewebide_hasher", ModelUtilities.encryptPassword(plainPassword),
                Scala.<String>Option(null));
    }

    /**
     * <p>Checks whether a supplied password matches the hashed one.</p>
     *
     * @param passwordInfo The password retrieved from the backing store (by means of UserService).
     * @param suppliedPassword The password supplied by the user trying to log in.
     *
     * @return True if the password matches, false otherwise.
     */
    @Override
    public boolean matches(PasswordInfo passwordInfo, String suppliedPassword) {
        return passwordInfo.password().equals(ModelUtilities.encryptPassword(suppliedPassword));
    }
}