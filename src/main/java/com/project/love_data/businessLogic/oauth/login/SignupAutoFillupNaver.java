package com.project.love_data.businessLogic.oauth.login;

import com.project.love_data.model.user.NaverUserInfo;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

public class SignupAutoFillupNaver {
    public RedirectAttributes excute(
            NaverUserInfo naverUserInfo,
            RedirectAttributes redirectAttributes
    ) {
        String pwd = null, nickname = null, new_name = null;
        List<String> email = null;

        email = emailParser(naverUserInfo.getEmail());
        pwd = naverUserInfo.getId();
        nickname = naverUserInfo.getNickname();


        if (email != null) {
            redirectAttributes.addFlashAttribute("str_email01", email.get(0));
            redirectAttributes.addFlashAttribute("str_email02", email.get(1));
        }

        if (pwd != null) {
            redirectAttributes.addFlashAttribute("pwd1", pwd);
            redirectAttributes.addFlashAttribute("pwd2", pwd);
        }

        if (nickname != null) {
            redirectAttributes.addFlashAttribute("nickname", nickname);
        }

        return redirectAttributes;
    }

    private List<String> emailParser(String email) {
        StringTokenizer strToken = null;
        List<String> strHoler = null;

        if (email == null) {
            return null;
        }

        strToken = new StringTokenizer(email, "@");

        while (strToken.hasMoreElements()){
            strHoler.add((String) strToken.nextElement());
        }

        return strHoler;
    }
}
