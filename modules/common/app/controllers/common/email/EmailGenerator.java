package controllers.common.email;

import javax.inject.Inject;
import play.Configuration;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Http;
import views.html.common.email.confirmation;
import views.html.common.email.resetPassword;
import views.html.common.email.resetSuccess;
import views.html.common.email.welcome;

/**
 * <p>This class contains different email utility methods.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class EmailGenerator {

    // ===========================================================
    // Global Variables
    // ===========================================================

    /** <p>Play Framework's simple mailer client</p> */
    @Inject
    private MailerClient myMailerClient;

    /** <p>Class that retrieves configurations</p> */
    @Inject
    private Configuration myConfiguration;

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
        Email email = generateEmailObject(userEmail, "Welcome to RESOLVE WebIDE",
                welcome.render(firstName, userEmail, link).body());
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
        String link = formCurrentWebPath() + "/confirm?c_code=" + confirmationCode + "&email=" + userEmail;
        Email email = generateEmailObject(userEmail, "RESOLVE WebIDE Registration Confirmation",
                confirmation.render(firstName, link).body());
        myMailerClient.send(email);
    }

    /**
     * <p>Generate and send an email with the necessary information to resetSuccess the
     * password for the specified user.</p>
     *
     * @param firstName User's first name.
     * @param userEmail User's email.
     * @param confirmationCode User's generated confirmation code.
     */
    public void generateResetPasswordEmail(String firstName, String userEmail, String confirmationCode) {
        String link = formCurrentWebPath() + "/reset?c_code=" + confirmationCode + "&email=" + userEmail;
        Email email = generateEmailObject(userEmail, "RESOLVE WebIDE Password Recovery",
                resetPassword.render(firstName, link).body());
        myMailerClient.send(email);
    }

    /**
     * <p>Generate and send an email confirming that we have successfully resetSuccess the
     * password for the specified user.</p>
     *
     * @param firstName User's first name.
     * @param userEmail User's email.
     */
    public void generateResetSuccessEmail(String firstName, String userEmail) {
        Email email = generateEmailObject(userEmail, "RESOLVE WebIDE Password Successfully Reset",
                resetSuccess.render(firstName, userEmail).body());
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
        String host = myConfiguration.getString("webide.emailhost");
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