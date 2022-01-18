package com.evgeniy.email;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

public class SendEmailTLS {
    public void SendEmail(String subjectMessage, String addressTo, String textOfMessage) {
        try {
            final String username = "recautosystem@gmail.com";
            final String password = "bfhodqkpdlnebkaz";
//
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // creates a new e-mail message
            Message message = new MimeMessage(session);
            message.setSubject(subjectMessage);

            Address addressTO = new InternetAddress(addressTo);
            message.setRecipient(Message.RecipientType.TO, addressTO);
            MimeMultipart multipart = new MimeMultipart();
//            MimeBodyPart attachment = new MimeBodyPart();
//            attachment.attachFile(new File("/zhenya2009/logs/app.log"));
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(textOfMessage, "text/html");
            multipart.addBodyPart(messageBodyPart);
//            multipart.addBodyPart(attachment);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }
}
