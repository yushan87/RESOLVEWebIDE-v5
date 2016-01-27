package controllers.webide;

import javax.inject.Inject;
import models.common.database.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Http;

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
        Email email = generateEmailObject(user.email);
        /*setSubject("Welcome to RESOLVE Web IDE");
        addRecipient(user.email);
        setFrom("Clemson RSRG <do_not_reply@" + Http.Request.current().host + ">");

        String link = Http.Request.current().getBase() + (String)Play.configuration.get("http.path") + "/";
        send(user, link);*/
    }

    public void generateConfirmationEmail(User user) {
        Email email = generateEmailObject(user.email);
        /*String confirmationCode = user.confirmationCode;
        setSubject("RESOLVE Web IDE Registration Confirmation");
        addRecipient(user.email);
        setFrom("Clemson RSRG <do_not_reply@" + Http.Request.current().host + ">");

        String link = Http.Request.current().getBase() + (String)Play.configuration.get("http.path") + "/confirm?c_code=" + confirmationCode + "&email=" + user.email;
        send(user, link);*/
    }

    public void lostPassword(User user) {
        Email email = generateEmailObject(user.email);
        /*String confirmationCode = user.confirmationCode;
        setSubject("RESOLVE Web IDE Password Recovery");
        addRecipient(user.email);
        setFrom("Clemson RSRG <do_not_reply@" + Http.Request.current().host + ">");

        String link = Http.Request.current().getBase() + (String)Play.configuration.get("http.path") + "/reset?c_code=" + confirmationCode + "&email=" + user.email;
        send(user, link);*/
    }

    public void resetPassword(User user) {
        Email email = generateEmailObject(user.email);
        /*setSubject("RESOLVE Web IDE Password Reset");
        addRecipient(user.email);
        setFrom("Clemson RSRG <do_not_reply@" + Http.Request.current().host + ">");
        send(user);*/
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>An helper method to create an email object.</p>
     *
     * @param emailAddress The recipient's email address.
     *
     * @return A new email object with the sender and recipient information.
     */
    private Email generateEmailObject(String emailAddress) {
        Email email = new Email();
        email.addTo(emailAddress);
        email.setFrom("Clemson RSRG <do_not_reply@" + Http.Context.current().request().host() + ">");

        return email;
    }
}