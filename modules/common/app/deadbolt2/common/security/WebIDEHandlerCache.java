package deadbolt2.common.security;

import be.objectify.deadbolt.java.ConfigKeys;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.cache.HandlerCache;
import deadbolt2.common.security.WebIDEDeadboltHandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>An implementation of {@link HandlerCache} that will be used
 * by {@code Deadbolt2}.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
@Singleton
public class WebIDEHandlerCache implements HandlerCache {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>The Deadbolt handler in use.</p> */
    private final DeadboltHandler defaultHandler;

    /** <p>A map of all handlers. Currently we only have one.</p> */
    private final Map<String, DeadboltHandler> handlers = new HashMap<>();

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * <p>This creates the {@link HandlerCache} that keeps track of
     * all the different {@link DeadboltHandler DeadboltHandlers} we have.</p>
     *
     * @param ecProvider the execution context
     */
    @Inject
    public WebIDEHandlerCache(final ExecutionContextProvider ecProvider) {
        defaultHandler = new WebIDEDeadboltHandler(ecProvider);
        handlers.put(ConfigKeys.DEFAULT_HANDLER_KEY, defaultHandler);
    }

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Applies this function to the given argument.</p>
     *
     * @param key a key for a {@link DeadboltHandler}.
     *
     * @return the corresponding {@link DeadboltHandler} if found,
     * {@code null} otherwise.
     */
    @Override
    public final DeadboltHandler apply(final String key) {
        return handlers.get(key);
    }

    /**
     * <p>Returns the default handler.</p>
     *
     * @return the default handler
     */
    @Override
    public final DeadboltHandler get() {
        return defaultHandler;
    }

}