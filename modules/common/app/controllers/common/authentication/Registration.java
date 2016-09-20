package controllers.common.authentication;

import javax.inject.Inject;
import models.common.form.RegistrationForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.authentication.registration;
import views.html.common.authentication.registrationSuccess;

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
    @AddCSRFToken
    public Result index() {
        String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
        return ok(registration.render(myFormFactory.form(RegistrationForm.class), token));
    }

    /**
     * <p>This handles the registration form submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional(readOnly = true)
    public Result handleSubmit() {
        Form<RegistrationForm> userForm = myFormFactory.form(RegistrationForm.class).bindFromRequest();
        if (userForm.hasErrors()) {
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
            return badRequest(registration.render(userForm, token));
        } else {
            RegistrationForm form = userForm.get();
            return ok(registrationSuccess.render());
        }
    }

}