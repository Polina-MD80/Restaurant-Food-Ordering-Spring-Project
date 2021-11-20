package softuni.restaurant.service.emails;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

        private final JavaMailSender mailSender;
        private final SimpleMailMessage preConfiguredMessage;

    public EmailService(JavaMailSender mailSender, SimpleMailMessage preConfiguredMessage) {
        this.mailSender = mailSender;
        this.preConfiguredMessage = preConfiguredMessage;
    }

    /**
         * This method will send compose and send the message
         * */
        public void sendMail(String to, String subject, String body)
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }

        /**
         * This method will send a pre-configured message
         * */
        public void sendPreConfiguredMail(String message)
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }
    }
