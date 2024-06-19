package com.qa.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtils {

    public static void sendEmailWithReportURL(String subject, String body) {
        final String username = System.getenv("SMTP_USERNAME"); // Fetch SMTP username from environment variable
        final String password = System.getenv("SMTP_PASSWORD"); // Fetch SMTP password from environment variable

        // Path to email addresses properties file
        String emailPropertiesFilePath = "src/main/resources/email_addresses.properties";

        // Load email addresses from properties file
        Properties emailProps = new Properties();
        try {
            emailProps.load(EmailUtils.class.getClassLoader().getResourceAsStream(emailPropertiesFilePath));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Combine all email addresses into a single string separated by commas
        String to = emailProps.values().stream()
                .map(Object::toString)
                .reduce((address1, address2) -> address1 + "," + address2)
                .orElse("");

        // SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a mail session with SMTP authentication
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Set sender email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Set recipients
            message.setSubject(subject); // Set email subject
            message.setText(body); // Set email body

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully....");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
