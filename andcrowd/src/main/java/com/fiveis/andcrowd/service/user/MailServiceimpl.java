package com.fiveis.andcrowd.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceimpl implements MailService{
    @Value("${mail.from_email}")
    private String fromEmail;

    @Value("${mail.email_password}")
    private String password;

    @Override
    public String getRandomNumber() {
        String randomNumber = Integer.toString((int) (Math.random()*1000000));
        return randomNumber.length() < 6 ? "0" + randomNumber : randomNumber;
    }

    @Override
    public String setEmail(String userEmail){
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        String randomNumber = getRandomNumber();

        String subject = "인증번호입니다.";
        String body = "아래의 인증번호를 입력해주세요\n" + randomNumber + "\n";

        if(sendMail(session, userEmail, subject, body) == null) return null;
        return randomNumber;
    }

    @Override
    public String sendMail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
            return "Work";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
