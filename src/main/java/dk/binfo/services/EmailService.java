package dk.binfo.services;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A class with methods to handle emails
 * so far it can only handle gmail.
 *
 * @author Stonie
 */
@Service("emailService")
public class EmailService {

    /**
     * A simple method to send emails using Gmail
     * using param user and password to login
     *
     * @param To The email we should send to
     * @param Subject The subject of the email
     * @param Body The body of the email, you can use html tags like <br>
     * @author Stonie
     */
    public void generateAndSendEmail(String To,String Subject,String Body){
    try {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;
        // setup Mail Server Properties
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // get Mail Session
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(To));
        generateMailMessage.setSubject(Subject);
        String emailBody = Body;
        generateMailMessage.setContent(emailBody, "text/html; charset=UTF-8");

        // Get Session and Send mail
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        transport.connect("smtp.gmail.com", "testbinfo1234", "testbinfo1234testbinfo1234");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
