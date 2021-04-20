package com.project.love_data.controller;

import com.project.love_data.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
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

    @RequestMapping(value ="/mail" ,method = RequestMethod.POST)
    public String execMail(
            @RequestParam(value = "address")String address,
            @RequestParam(value = "domain")String domain
             ) {
        log.info("mail C");
        log.info("addess="+address);
        log.info("addess="+domain);

        String mail = userRepository.email_send(address+"@"+domain);
        log.info("mail="+mail);
        mailService.mailSend(mail);

        return "redirect:/";
    }
}