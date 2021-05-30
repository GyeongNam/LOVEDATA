package com.project.love_data.businessLogic.account;

import com.project.love_data.model.user.User;
import com.project.love_data.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Log4j2
@Service
public class UserAccountDelete {

    public String execute(HttpServletRequest request,
                          Principal principal,
                          UserRepository userRepository) {
        Optional<User> user = null;

        log.info("logined id : " + principal.getName());
        user = userRepository.findUserByEmail(principal.getName());

        if (user.isPresent()) {
            log.info("user 정보 존재");
            log.info(user);
        } else {
            log.info("user 정보 없음");
            return "redirect:/";
        }

        userRepository.deleteUserByEmail(user.get().getUser_email());
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, null, null);

        log.info("유저 정보가 남아있는지 확인");
        user = userRepository.findUserByEmail(principal.getName());

        if (user.isPresent()) {
            log.info("user 정보 삭제 안됨");
            log.info(user);
        } else {
            log.info("user 정보 삭제됨");
            return "redirect:/";
        }

        userRepository.delete(user.get());

        return "redirect:/";
    }
}
