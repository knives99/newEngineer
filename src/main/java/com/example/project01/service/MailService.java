package com.example.project01.service;

import com.example.project01.config.MailConfig;
import com.example.project01.entity.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class MailService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final JavaMailSenderImpl mailSender;
    private final long tag;
    private final List<String> mailMessages;
    private final String LOG_EMAIL;

    public MailService(JavaMailSenderImpl mailSender){
        this.mailSender = mailSender;
        this.tag = System.currentTimeMillis();
        this.mailMessages = new ArrayList<>();
        this.LOG_EMAIL = mailSender.getUsername();

    }


    public void sendMail(String subject, String content, List<String> receivers) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender.getUsername());
        message.setTo(receivers.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            mailMessages.add(content);
            printMessages();
        } catch (MailAuthenticationException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public void sendNewProductMail(String id) {
        String content = String.format("There is ($s)",id);
        sendMail("New Product", content,
                Collections.singletonList(LOG_EMAIL));
    }

    private void printMessages() {
        System.out.println("----------");
        mailMessages.forEach(System.out::println);
    }


    @PreDestroy
    public void preDestroy() {
        System.out.println("##########");
        System.out.printf("Spring Boot is about to destroy Mail Service %d.\n\n", tag);
    }
}
