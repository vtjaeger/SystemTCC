package com.tcc.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String texto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(texto);

        mailSender.send(message);

    }
}
