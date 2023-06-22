package pl.edu.pb.wi.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LogManager.getLogger(EmailService.class);

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.mailSender = javaMailSender;
    }

    public void sendOrderConfirmation(String recipentEmail) {
        logger.info("In sendOrderConfirmation");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipentEmail);
        simpleMailMessage.setSubject("Order confirmation");
        simpleMailMessage.setText("Confirmation you have successfully ordered your items. :)");

        mailSender.send(simpleMailMessage);
        logger.info("Success sending email");
    }
}
