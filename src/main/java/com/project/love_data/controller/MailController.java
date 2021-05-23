package com.project.love_data.controller;

import com.project.love_data.businessLogic.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

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

/*    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }*/

    @ResponseBody
    @RequestMapping(value ="/mail" ,method = RequestMethod.POST)
    public Map<String,String> execMail(@RequestBody HashMap<String, String> data){
        Map<String, String> map = new HashMap<String, String>();

        String address = data.get("address");
        String domain = data.get("domain");

        log.info("ad : " + address);
        log.info("do : " + domain);


        String mail = userRepository.email_send(address+"@"+domain);
        log.info("mail="+mail);
        if(mail ==null){
            map.put("msg" , "0");
            return map;
        }
        else{
            mailService.mailSend(mail);
            map.put("msg" , "1");
            return map;
        }
    }
}