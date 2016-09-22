package controllers.common.security;

import javax.inject.Inject;
import models.common.form.LoginForm;
import play.Configuration;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.filters.csrf.AddCSRFToken;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.common.security.index;

/**
 * <p>This class serves as a controller class for handling all security
 * related actions including login and logout.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class Security extends Controller {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Form factory</p> */
    @Inject
    private FormFactory myFormFactory;

    /** <p>Class that retrieves configurations</p> */
    @Inject
    private Configuration myConfiguration;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>This renders the login page for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    public Result index() {
        // Check the session to see if the request comes from an user
        // that has logged in already.
        String user = session().remove("connected");
        if (user != null) {
            // Obtain the email host from the configuration file
            String context = myConfiguration.getString("play.http.context");
            if (context == null) {
                throw new RuntimeException("Missing configuration: Play HTTP Context");
            }

            return redirect(context + "/");
        }
        else {
            String token = CSRF.getToken(request()).map(t -> t.value()).orElse("no token");
            return ok(index.render(myFormFactory.form(LoginForm.class), token));
        }
    }

    /**
     * <p>This handles the login form submission for the WebIDE.</p>
     *
     * @return The result of rendering the page.
     */
    @AddCSRFToken
    @RequireCSRFCheck
    @Transactional
    public Result login() {
        return ok("Not done!");
    }

}