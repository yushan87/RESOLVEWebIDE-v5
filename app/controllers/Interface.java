package controllers;

//  Play Imports
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * TODO: Write a description of this module
 */
public class Interface extends Controller {

    /**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    public Result index() {
        return ok(index.render());
    }

}
