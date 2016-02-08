package controllers.common.email;

import javax.inject.Inject;
import play.Play;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Http;
import views.html.common.email.confirmation;
import views.html.common.email.lostPassword;
import views.html.common.email.reset;
import views.html.common.email.welcome;

/**
 * TODO: Add JavaDocs for this class.
 */
public class EmailGenerator {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Play framework's simple emailer</p> */
    @Inject
    private MailerClient myMailerClient;

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Generate and send a welcome email with
     * the specified user information.</p>
     *
     * @param firstName User's first name.
     * @param userEmail User's email.
     */
    public void generateWelcomeEmail(String firstName, String userEmail) {
        String link = formCurrentWebPath();
        Email email = generateEmailObject(userEmail, "Welcome to RESOLVE Web IDE", welcome.render(firstName, userEmail, link).body());
        myMailerClient.send(email);
    }

    /**
     * <p>Generate and send a confirmation email with
     * the specified user information.</p>
     *
     * @param firstName User's first name.
     * @param userEmail User's email.
     * @param confirmationCode User's generated confirmation code.
     */
    public void generateConfirmationEmail(String firstName, String userEmail, String confirmationCode) {
        String link = formCurrentWebPath() + "confirm?c_code=" + confirmationCode + "&email=" + userEmail;
        Email email = generateEmailObject(userEmail, "RESOLVE Web IDE Registration Confirmation", confirmation.render(firstName, link).body());
        myMailerClient.send(email);
    }

    /**
     * <p>Generate and send a lost password email with
     * the specified user information.</p>
     *
     * @param firstName User's first name.
     * @param userEmail User's email.
     * @param confirmationCode User's generated confirmation code.
     */
    public void generateLostPasswordEmail(String firstName, String userEmail, String confirmationCode) {
        String link = formCurrentWebPath() + "reset?c_code=" + confirmationCode + "&email=" + userEmail;
        Email email = generateEmailObject(userEmail, "RESOLVE Web IDE Password Recovery", lostPassword.render(firstName, link).body());
        myMailerClient.send(email);
    }

    /**
     * <p>Generate and send a reset password email with
     * the specified user information.</p>
     *
     * @param firstName User's first name.
     * @param userEmail User's email.
     */
    public void generateResetPasswordEmail(String firstName, String userEmail) {
        Email email = generateEmailObject(userEmail, "RESOLVE Web IDE Password Reset", reset.render(firstName, userEmail).body());
        myMailerClient.send(email);
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>An helper method to generate the current web path.</p>
     *
     * @return A string of the format
     */
    private String formCurrentWebPath() {
        String version = Http.Context.current().request().version();
        String protocol;
        if (version.startsWith("HTTPS")) {
            protocol = "https://";
        }
        else {
            protocol = "http://";
        }

        return protocol + Http.Context.current().request().host() + Http.Context.current().request().path();
    }

    /**
     * <p>An helper method to create an email object.</p>
     *
     * @param emailAddress The recipient's email address.
     * @param subject The subject of the email.
     * @param body The body of the email. This must be in HTML format.
     *
     * @return A new email object with the sender and recipient information.
     */
    private Email generateEmailObject(String emailAddress, String subject, String body) {
        // Obtain the email host from the configuration file
        String host = Play.application().configuration().getString("webide.emailhost");
        if (host == null) {
            throw new RuntimeException("Missing configuration: Email Host");
        }

        // Generate the email
        Email email = new Email();
        email.addTo(emailAddress);
        email.setFrom("Clemson RSRG <do_not_reply@" + host + ">");
        email.setSubject(subject);
        email.setBodyHtml(body);

        return email;
    }
}