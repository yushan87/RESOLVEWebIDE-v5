package controllers.common.authentication;

import controllers.common.email.EmailGenerator;
import javax.inject.Inject;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.authentication.registration;

/**
 * TODO: Add JavaDocs for this class.
 */
public class Registration extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>An email generator.</p> */
    @Inject
    private EmailGenerator myEmailGenerator;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the registration page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
    public Result index() {
        return ok(registration.render());
    }

    public Result handleSubmit() {
        DynamicForm requestData = Form.form().bindFromRequest();
        String firstname = requestData.get("firstName");
        String lastname = requestData.get("lastName");

        return ok("Hello " + firstname + " " + lastname);
    }
}