package com.example.project01.controller;

import com.example.project01.config.MailConfig;
import com.example.project01.entity.SendMailRequest;
import com.example.project01.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/mail",produces = "application/json")
public class MailController {

    @Autowired
    MailService mailService;

    public ResponseEntity<Void> sendMail(@Valid  @RequestBody SendMailRequest request){
        mailService.sendMail(request);
        return ResponseEntity.noContent().build();
    }


}
