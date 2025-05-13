package oopproject.rpmsfinal;

import java.io.Serial;
import java.io.Serializable;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.List;

/**
 * Handles email notifications using JavaMail API with singleton pattern
 */
public class EmailNotification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static EmailNotification instance;

    // SMTP server configuration
    private String host = "smtp.gmail.com";
    private int port = 587;
    private String username = "realtimepatientmanagement@gmail.com"; // Will be set through UI
    private String password = "nwxc lnnk zigi jpki"; // Will be set through UI

    private EmailNotification() {
        // Private constructor for singleton
    }

    public static synchronized EmailNotification getInstance() {
        if (instance == null) {
            instance = new EmailNotification();
        }
        return instance;
    }

    public void sendNotification(String email, String subject, String message) {


        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.ssl.trust", host);

            javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            Transport.send(emailMessage);
            System.out.println("Email sent to user" );

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}