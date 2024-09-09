package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.entity.BasketItem;
import com.example.getiryemek_clone.service.SendEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailServiceImpl implements SendEmailService {


    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${mail.from}")
    private String mailFrom ;

    public void sendPaymentEmail(String to,
                                 String customerName, String restaurantName, String restaurantPhone,
                                 List<BasketItem> orderItems, BigDecimal totalAmount) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("customerName", customerName);
        context.setVariable("restaurantName", restaurantName);
        context.setVariable("restaurantPhone", restaurantPhone);
        context.setVariable("orderItems", orderItems);
        context.setVariable("totalAmount", totalAmount);

        String subject="Ödemeniz başarıyla alınmıştır";

        String htmlContent = templateEngine.process("orderConfirmationTemplate.html", context);

        helper.setFrom(mailFrom);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);

    }

    public void sendCreatePassword(String to , String name , String link ) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Context context =  new Context();

        context.setVariable("customerMail", to);
        context.setVariable("name", name);
        context.setVariable("link", link);
        context.setVariable("sentTime" , LocalTime.now());

        String title = "Şifre Oluşturma Talebi" ;

        String htmlContent = templateEngine.process("newPassword.html", context);

        helper.setFrom(mailFrom);
        helper.setTo(to);
        helper.setSubject(title);
        helper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);

    }
}
