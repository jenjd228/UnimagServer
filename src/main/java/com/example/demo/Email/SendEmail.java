package com.example.demo.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

    @Autowired
    private MailSender mailSender;

    public void sendEmail(String message, String mail){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail);
        msg.setFrom("sendTest228@gmail.com");
        msg.setSubject("Код подтверждения для YniMag");
        msg.setText("Код подтверждения: "+ message);
        mailSender.send(msg);
    }
}
