package org.sinhala.wordnet.css.common.mail;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static org.sinhala.wordnet.css.common.Constants.Properties.MAIL_AUTH;
import static org.sinhala.wordnet.css.common.Constants.Properties.MAIL_AUTH_PASSWORD;
import static org.sinhala.wordnet.css.common.Constants.Properties.MAIL_AUTH_USERNAME;
import static org.sinhala.wordnet.css.common.Constants.Properties.MAIL_HOST_NAME;
import static org.sinhala.wordnet.css.common.Constants.Properties.MAIL_PORT;

public class MailHandler {

    public static void sendMail(String to, String from, String subject, String content) {

        String host = System.getProperty(MAIL_HOST_NAME);
        boolean authEnabled = Boolean.getBoolean(MAIL_AUTH);
        int port = Integer.getInteger(MAIL_PORT, 587);

        // Get system properties
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", String.valueOf(authEnabled));
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", String.valueOf(port));
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = System.getProperty(MAIL_AUTH_USERNAME, "");
                String password = System.getProperty(MAIL_AUTH_PASSWORD, "");
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));

            // Set Subject: header field
            message.setSubject(subject);

            Multipart mp = new MimeMultipart("alternative");

            MimeBodyPart html = new MimeBodyPart();
            html.setContent(content, "text/html");

            mp.addBodyPart(html);


            // Now set the actual message
            message.setContent(mp);

            // Send message
            Transport.send(message);

        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}