/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.model;

import static com.ruzzz.nemo.properties.LoggerConfig.errorLogger;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import raven.toast.Notifications;

/**
 *
 * @author Acer
 */
public class EmailSender {

    public String sendEmail(String mailSubject, String mailBody, String email) {
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Processing....");
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Waite Few Seconds.....");
        String to = email;
        String from = "medagamathiwain@gmail.com";
        String subject = mailSubject;
        String htmlBody = mailBody;

        String host = "smtp.gmail.com";
        String port = "587";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Replace with your Gmail credentials
        String username = "medagamathiwain@gmail.com";
        String password = "jafj cqvt gcwf btqi";

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
            message.setContent(htmlBody, "text/html");

            Transport.send(message);
            return "OK";
        } catch (MessagingException mex) {
            mex.printStackTrace();
            errorLogger.warning("Email Sender Exception; Error: " + mex);
            return "Failed to send email: " + mex.getMessage();
        }
    }
}
