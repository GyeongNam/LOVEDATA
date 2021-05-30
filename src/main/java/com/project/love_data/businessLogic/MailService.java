package com.project.love_data.businessLogic;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;

    public void mailSend(String mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("12qwqq@gmail.com");
        message.setSubject("LOVEDATA 비밀번호 재설정 안내 메일입니다.");
        message.setText("LOVEDATA 비밀번호 재설정 안내 메일입니다 아래의 링크로 들어가서 비밀번호를 재설정 해 주세요");


        mailSender.send(message);
    }

    public static String getRandomStr(int size) {
        if(size > 0) {
            char[] tmp = new char[size];
            for(int i=0; i<tmp.length; i++) {
                int div = (int) Math.floor( Math.random() * 2 );

                if(div == 0) { // 0이면 숫자로
                    tmp[i] = (char) (Math.random() * 10 + '0') ;
                }else { //1이면 알파벳
                    tmp[i] = (char) (Math.random() * 26 + 'A') ;
                }
            }
            return new String(tmp);
        }
        return "ERROR : Size is required.";
    }
}
