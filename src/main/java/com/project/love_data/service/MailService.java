package com.project.love_data.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;

    public void mailSend(String mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("12qwqq@gmail.com");
        message.setSubject("LOVEDATA 비밀번호 재설정 안내 메일입니다.");
        message.setText("아래의 링크로 들어가서 비밀번호를 재설정 해 주세요");

        mailSender.send(message);
    }
}
