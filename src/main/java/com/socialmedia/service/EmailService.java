package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome!");
            message.setText("Hello " + username + ", welcome!");

            mailSender.send(message);
            System.out.println("✅ Email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Email send failed: " + e.getMessage());
            e.printStackTrace(); // <-- Shows actual error
        }
    }
}