package controllers;

//  Play Imports
import play.mvc.*;

// HTML pages from the Views package
import views.html.*;

/**
 * TODO: Write a description of this module
 */
public class Interface extends Controller {

    /**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    public static Result index() {
        return ok(index.render());
    }

}
