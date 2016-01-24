package controllers.webide;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.webide.index;

/**
 * TODO: Write a description of this module
 */
public class Interface extends Controller {

    /**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    @Transactional
    public Result index() {
        return ok(index.render());
    }

}