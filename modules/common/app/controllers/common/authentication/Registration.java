package controllers.common.authentication;

import javax.inject.Inject;
import models.common.form.RegistrationForm;
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

    /** <p>Form factory</p> */
    @Inject
    private FormFactory myFormFactory;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the registration page for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    public Result index() {
        return ok(registration.render(null));
    }

    /**
     * <p>This handles the registration form submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    public Result handleSubmit() {
        Form<RegistrationForm> userForm = myFormFactory.form(RegistrationForm.class).bindFromRequest();
        if (userForm.hasErrors()) {
            return badRequest(registration.render(userForm));
        } else {
            RegistrationForm form = userForm.get();
            return ok("Hello " + form.getFirstName() + " " + form.getLastName());
        }
    }

}