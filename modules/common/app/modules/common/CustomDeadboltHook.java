package modules.common;

import be.objectify.deadbolt.java.cache.HandlerCache;
import deadbolt2.common.security.WebIDEHandlerCache;
import javax.inject.Singleton;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * <p>Creates a binding for our handler and cache implementations for {@code Deadbolt2}.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class CustomDeadboltHook extends Module {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Binds {@link WebIDEHandlerCache} to {@link HandlerCache}.</p>
     *
     * @param environment The environment.
     * @param configuration The configuration.
     *
     * @return A sequence of bindings.
     */
    @Override
    public final Seq<Binding<?>> bindings(final Environment environment, final Configuration configuration) {
        return seq(bind(HandlerCache.class).to(WebIDEHandlerCache.class).in(Singleton.class));
    }

}