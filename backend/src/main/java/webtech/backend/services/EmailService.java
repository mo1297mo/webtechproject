package webtech.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * This method is used to send a simple email with html content
     * @param to
     * @param subject
     * @param htmlContent
     */
    public void sendEmail(String to, String subject, String htmlContent) {
        Dotenv dotenv = Dotenv.load();
        String senderEmail = dotenv.get("MAIL_USERNAME");
        if (senderEmail == null) {
            throw new IllegalStateException("sender email is not set in .env");
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // error correction
        }
    }
}
