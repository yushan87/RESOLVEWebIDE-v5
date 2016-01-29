package controllers.common.securesocial;

import play.libs.F;
import securesocial.core.BasicProfile;
import securesocial.core.PasswordInfo;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;
import securesocial.core.services.SaveMode;

/**
 * TODO: Add JavaDocs for this class.
 */
public class CustomUserService extends BaseUserService {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Saves the Identity. This method gets called when a user logs in.
     * This is your chance to save the user information in your backing store.</p>
     *
     * @param user
     * @param mode
     */
    @Override
    public F.Promise doSave(BasicProfile user, SaveMode mode) {
        return null;
    }

    /**
     * <p>Saves a token.</p>
     *
     * <p>Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation.</p>
     *
     * @param token
     */
    @Override
    public F.Promise<Token> doSaveToken(Token token) {
        return null;
    }

    /**
     * <p>Links the current user Identity to another.</p>
     *
     * @param current The Identity of the current user
     * @param to      The Identity that needs to be linked to the current user
     */
    @Override
    public F.Promise doLink(Object current, BasicProfile to) {
        return null;
    }

    /**
     * <p>Finds the user in the backing store.</p>
     *
     * @param providerId
     * @param userId
     *
     * @return an Identity instance or null if no user matches the specified id
     */
    @Override
    public F.Promise<BasicProfile> doFind(String providerId, String userId) {
        return null;
    }

    @Override
    public F.Promise<PasswordInfo> doPasswordInfoFor(Object user) {
        return null;
    }

    @Override
    public F.Promise<BasicProfile> doUpdatePasswordInfo(Object user, PasswordInfo info) {
        return null;
    }

    /**
     * Finds a token
     * <p>
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation
     *
     * @param tokenId the token id
     * @return a Token instance or null if no token matches the specified id
     */
    @Override
    public F.Promise<Token> doFindToken(String tokenId) {
        return null;
    }

    /**
     * Finds an identity by email and provider id.
     * <p>
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation.
     *
     * @param email      - the user email
     * @param providerId - the provider id
     * @return an Identity instance or null if no user matches the specified id
     */
    @Override
    public F.Promise<BasicProfile> doFindByEmailAndProvider(String email, String providerId) {
        return null;
    }

    /**
     * Deletes a token
     * <p>
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation
     *
     * @param uuid the token id
     */
    @Override
    public F.Promise<Token> doDeleteToken(String uuid) {
        return null;
    }

    /**
     * Deletes all expired tokens
     * <p>
     * Note: If you do not plan to use the UsernamePassword provider just provide en empty
     * implementation
     */
    @Override
    public void doDeleteExpiredTokens() {

    }
}