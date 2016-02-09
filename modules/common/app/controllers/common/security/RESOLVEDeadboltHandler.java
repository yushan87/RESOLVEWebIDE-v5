package controllers.common.security;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import java.util.Optional;

/**
 * TODO: Add JavaDocs for this class.
 */
public class RESOLVEDeadboltHandler extends AbstractDeadboltHandler {

    @Override
    public F.Promise<Optional<Result>> beforeAuthCheck(Http.Context context) {
        return null;
    }

}