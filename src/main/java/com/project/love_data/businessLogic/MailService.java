package com.project.love_data.businessLogic;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;

    public void mailSend(String mail, String randomArr) {
        log.info("RandomArr" + randomArr);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("12qwqq@gmail.com");
        message.setSubject("LOVEDATA 비밀번호 재설정 안내 메일입니다.");
        message.setText(
                "LOVEDATA 비밀번호 재설정 안내 메일입니다 아래의 링크로 들어가서 비밀번호를 재설정 해 주세요\n" +
                        "https://lovedata.duckdns.org/NewPassword/" + randomArr
        );
//        message.setText("http://localhost:8080/NewPassword");

        mailSender.send(message);
    }

    public void adminmailSend(String mail, String admessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("12qwqq@gmail.com");
        message.setSubject("LOVEDATA 안내 메일입니다.");
        message.setText(
                "LOVEDATA 에서 회원들에게 전송된 이메일입니다.\n"+ admessage);
//        message.setText("http://localhost:8080/NewPassword");

        mailSender.send(message);
    }

    public String getRandomStr(int size) {
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
