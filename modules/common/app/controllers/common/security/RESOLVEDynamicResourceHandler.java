package controllers.common.security;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.libs.F;
import play.mvc.Http;

/**
 * TODO: Add JavaDocs for this class.
 */
public class RESOLVEDynamicResourceHandler implements DynamicResourceHandler {

    /**
     * Check the access of the named resource.
     *
     * @param name            the resource name
     * @param meta            additional information on the resource
     * @param deadboltHandler the current {@link DeadboltHandler}
     * @param ctx             the context of the current request
     * @return true if access to the resource is allowed, otherwise false
     */
    @Override
    public F.Promise<Boolean> isAllowed(String name, String meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return null;
    }

    /**
     * Invoked when a custom pattern needs checking..
     *
     * @param permissionValue the permission value
     * @param deadboltHandler the current {@link DeadboltHandler}
     * @param ctx             the context of the current request
     * @return true if access based on the permission is  allowed, otherwise false
     */
    @Override
    public F.Promise<Boolean> checkPermission(String permissionValue, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return null;
    }
}