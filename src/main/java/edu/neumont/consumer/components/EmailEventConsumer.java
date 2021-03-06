package edu.neumont.consumer.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailEventConsumer {

    private final JavaMailSender emailSender;

    EmailEventConsumer(JavaMailSenderImpl emailSender)
    {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "emailQueue")
    public void sendSimpleMessage(String[] message){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(message[0]);
        email.setSubject("New Password");
        email.setText(String.format("New Password: %s", message[1]));
        emailSender.send(email);

    }

    @RabbitListener(queues = "serviceQueue")
    public void sendServiceUpdate(String[] message)
    {
        String emailTo = message[0];
        String subject = message[1];
        String body = message[2];
        if(emailTo.contains(" "))
        {
            String[] spilt = emailTo.split(" ");
            String serviceEmail = spilt[1];
            emailTo = spilt[0];
            SimpleMailMessage serviceEmailMessage = new SimpleMailMessage();
            serviceEmailMessage.setTo(serviceEmail);
            serviceEmailMessage.setSubject(subject);
            serviceEmailMessage.setText(body);
            emailSender.send(serviceEmailMessage);
        }
        SimpleMailMessage userEmail = new SimpleMailMessage();
        userEmail.setTo(emailTo);
        userEmail.setSubject(subject);
        userEmail.setText(body);
        emailSender.send(userEmail);
    }
}
