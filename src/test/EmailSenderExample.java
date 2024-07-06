package test;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSenderExample {

    public static void main(String[] args) {
        String to = "senutthiwain@gmail.com"; // Replace with recipient's email address
        String from = "medagamathiwain@gmail.com"; // Replace with your email address
        String subject = "Customized Email";
        String htmlBody = "<h1 style=\"color:blue;\">Custom HTML Email</h1>\n" +
                          "<p>This is a customized HTML email sent using JavaMailSender.</p>\n" +
                          "<p>You can include <strong>bold</strong>, <em>italic</em>, and other HTML tags.</p>";

        // SMTP server information
        String host = "smtp.gmail.com";
        String port = "587"; // TLS port

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Replace with your Gmail credentials
        String username = "medagamathiwain@gmail.com";
        String password = "uelt skyt idjl mazs"; // Replace with your Gmail app password

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html"); // Set HTML content

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
