package com.project.love_data.controller;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final JavaMailSender javaMailSender;

    MailBodyUtil mailBodyUtil = new MailBodyUtil();

}
