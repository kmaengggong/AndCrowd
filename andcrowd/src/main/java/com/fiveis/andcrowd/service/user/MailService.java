package com.fiveis.andcrowd.service.user;

import org.springframework.stereotype.Service;

import javax.mail.Session;

@Service
public interface MailService {
    String getRandomNumber();
    String setEmail(String userEmail);
    String sendMail(Session session, String toEmail, String subject, String body);
}
