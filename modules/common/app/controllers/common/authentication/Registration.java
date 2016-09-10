package controllers.common.authentication;

import controllers.common.email.EmailGenerator;
import javax.inject.Inject;

import models.common.ModelUtilities;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
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

    /** <p>Form factory</p> */
    @Inject
    private FormFactory myFormFactory;

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
        DynamicForm requestData = myFormFactory.form().bindFromRequest();
        String firstname = requestData.get("firstName");
        String lastname = requestData.get("lastName");
        String email = requestData.get("email");
        String encryptedPassword = ModelUtilities.encryptPassword(requestData.get("password"));

        myEmailGenerator.generateConfirmationEmail(firstname, email, "d");

        return ok("Hello " + firstname + " " + lastname);
    }
}