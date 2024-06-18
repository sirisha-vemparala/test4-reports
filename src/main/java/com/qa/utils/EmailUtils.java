package com.qa.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;

public class EmailUtils {
    public static void sendEmailWithAttachment(String subject, String body, String attachmentPath) {
        final String username = "sirishavemparala1203@gmail.com"; 
        final String password = "dxxf rnia azci edpa"; 

        Properties emailProps = new Properties();
        try (InputStream input = EmailUtils.class.getClassLoader().getResourceAsStream("email_addresses.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find email_addresses.properties");
                return;
            }
            emailProps.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String to = emailProps.values().stream()
            .map(Object::toString)
            .collect(Collectors.joining(","));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Create the message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Create the attachment body part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(attachmentPath);

            // Create Multipart object to hold both the message and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            // Set the content for the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
