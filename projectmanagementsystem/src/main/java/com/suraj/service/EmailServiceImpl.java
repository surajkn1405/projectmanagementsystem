package com.suraj.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithToken(String userEmail, String link) throws Exception {
        MimeMessage mimemessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimemessage, "utf-8");
        String subject = "join project team Invitation";
        String text = "Click the link to join the project team"+link;
        helper.setSubject(subject);
        helper.setText(text,true);
        helper.setTo(userEmail);

        try{
            javaMailSender.send(mimemessage);
        }catch (MailSendException e){
            throw new MailSendException("Faild to send email");
        }
    }
}
