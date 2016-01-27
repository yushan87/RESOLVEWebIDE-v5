package controllers.common;

import javax.inject.Inject;
import models.common.database.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Http;
import views.html.common.email.reset;

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

    public void generateWelcomeEmail(User user) {
        /*Email email = generateEmailObject(user.email, "Welcome to RESOLVE Web IDE", "");
        String link = Http.Request.current().getBase() + (String)Play.configuration.get("http.path") + "/";
        send(user, link);*/
    }

    public void generateConfirmationEmail(User user) {
        /*Email email = generateEmailObject(user.email, "RESOLVE Web IDE Registration Confirmation");
        String confirmationCode = user.confirmationCode;

        String link = Http.Request.current().getBase() + (String)Play.configuration.get("http.path") + "/confirm?c_code=" + confirmationCode + "&email=" + user.email;
        send(user, link);*/
    }

    public void lostPassword(User user) {
        /*Email email = generateEmailObject(user.email, "RESOLVE Web IDE Password Recovery");
        String confirmationCode = user.confirmationCode;

        String link = Http.Request.current().getBase() + (String)Play.configuration.get("http.path") + "/reset?c_code=" + confirmationCode + "&email=" + user.email;
        send(user, link);*/
    }

    public void resetPassword(User user) {
        String userEmail = user.email;
        String firstName = user.firstName;
        Email email = generateEmailObject(user.email, "RESOLVE Web IDE Password Reset", reset.render(firstName, userEmail).body());
        myMailerClient.send(email);
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

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
        Email email = new Email();
        email.addTo(emailAddress);
        email.setFrom("Clemson RSRG <do_not_reply@" + Http.Context.current().request().host() + ">");
        email.setSubject(subject);
        email.setBodyHtml(body);

        return email;
    }
}