package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.service.SendEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailServiceImpl implements SendEmailService {

    private final JavaMailSender javaMailSender;

    private String mailFrom = "infogetiryemek@gmail.com";

    public void sendEmail(String recipient , String body ,String subject){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(mailFrom);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);

        javaMailSender.send(simpleMailMessage);

        log.info("Mail sended successfully");
    }
}
