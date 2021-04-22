package com.project.love_data.service.account;

import com.project.love_data.model.user.User;
import com.project.love_data.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Log4j2
@Service
public class UserAccountDelete {

    public String execute(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session,
                           Principal principal,
                           UserRepository userRepository) {
        Optional<User> user = null;
        boolean user_roleset_check = false;
        boolean user_check = false;

        log.info("logined id : " + principal.getName());
        user = userRepository.findUserByEmail(principal.getName());

        if (user.isPresent()) {
            log.info("user 정보 존재");
            log.info(user);
        } else {
            log.info("user 정보 없음");
            return "redirect:/";
        }

//        userRepository.deleteUserRoleByEmail(user.get().getUser_no());

        userRepository.deleteUserByEmail(user.get().getUser_email());

        log.info("유저 정보 확인");
        user = userRepository.findUserByEmail(principal.getName());

        if (user.isPresent()) {
            log.info("user 정보 존재");
            log.info(user);
        } else {
            log.info("user 정보 없음");
            return "redirect:/";
        }

        return "redirect:/";
    }
}
