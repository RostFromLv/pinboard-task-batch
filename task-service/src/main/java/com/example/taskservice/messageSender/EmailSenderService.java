package com.example.taskservice.messageSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {

  private final JavaMailSender mailSender;

  @Autowired
  public EmailSenderService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String toEmail,String subject,String message){

    SimpleMailMessage mailMessage= new SimpleMailMessage();

    mailMessage.setFrom("rostyslavb99@gmail.com");
    mailMessage.setTo(toEmail);
    mailMessage.setText(message);
    mailMessage.setSubject(subject);

    mailSender.send(mailMessage);

  }
}
