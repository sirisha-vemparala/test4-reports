package com.qa.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {

    // Method to send email with report URL and SMTP credentials
    public static void sendEmailWithReportURL(String subject, String body, String smtpUsername, String smtpPassword, String[] recipients) {
        String host = "smtp.gmail.com"; // Replace with your SMTP host
        int port = 587; // Replace with your SMTP port
        String from = smtpUsername;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            InternetAddress[] toAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                toAddresses[i] = new InternetAddress(recipients[i]);
            }

            message.setRecipients(Message.RecipientType.TO, toAddresses);
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully to " + String.join(", ", recipients));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
