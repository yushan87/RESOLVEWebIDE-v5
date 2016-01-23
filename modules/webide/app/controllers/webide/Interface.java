package controllers.webide;

//  Play Imports
import models.common.database.User;
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
        User u = User.connect("yushans@clemson.edu", "12345");

        if (u == null) {
            System.out.println("Not in database");

            u = User.addUser("yushans@clemson.edu", "12345", "Yu-Shan", "Sun");
        }

        return ok(index.render());
    }

}