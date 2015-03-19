package controllers.admin;

//  Play Imports
import play.mvc.*;

/**
 * TODO: Write a description of this module
 */
public class Admin extends Controller {

    /**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    public static Result index() {
        return ok(views.html.admin.index.render());
    }

}
