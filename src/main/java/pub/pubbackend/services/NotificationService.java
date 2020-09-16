package pub.pubbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

@Component
public class NotificationService  {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEmail(String email, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("teodoraodanic96@gmail.com");
        msg.setFrom(email);
        msg.setSubject("Contact us!!");
        msg.setText(text);
        System.out.println(javaMailSender);
        try {
            javaMailSender.send(msg);
            return "success";
        } catch (Exception  e) {
            e.printStackTrace();
            System.out.println(e);
            return "fail";
        }

//        return "success";
    }
}
