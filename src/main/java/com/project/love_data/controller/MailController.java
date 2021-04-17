package com.project.love_data.controller;

import com.project.love_data.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MailController {

    @Autowired
    private final MailService mailService;

    @Autowired
    private com.project.love_data.repository.UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }

    @PostMapping("/mail")
    public void execMail(
            @RequestParam(value = "address")String address,
            @RequestParam(value = "domain")String domain
             ) {
        String mail = userRepository.email_check(address+"@"+domain);
        mailService.mailSend(mail);
    }
}
