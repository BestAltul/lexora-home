package com.lexorahome.Lexora.main.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(String toEmail, String resetLink){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Lexora: Reset your password");
        message.setText("Hello!\n\n" +
                "To reset your password, please click the link below:\n" +
                resetLink + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request a password reset, please ignore this email.\n\n" +
                "Best regards,\nLexora Team");
        try{javaMailSender.send(message);}catch (Exception e){

        }

    }
}


