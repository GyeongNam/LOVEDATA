package com.project.love_data.security.service;

import com.project.love_data.repository.UserRepository;
import com.project.love_data.model.User;
import com.project.love_data.security.model.AuthUserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService loadUserByUsername " + username);

//        Optional<User> result = userRepository.findUserByEmail_Social(username, false);
        Optional<User> result = userRepository.findUserByEmail_Privilege(username);

        // DB에 저장된 유저 정보가 없을 때
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email");
        }

        User user = result.get();

//        log.info("UserDetailsService user info : " + user.toString());
//        log.info("UserDetailsService userRole(SET) : " + user.getRoleSet());
//        og.info("UserDetailsService userRole : " + user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
        log.debug("UserDetailsService user info : " + user.toString());
        log.debug("UserDetailsService userRole(SET) : " + user.getRoleSet());
        log.debug("UserDetailsService userRole : " + user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));

        // ToDo : 2021-04-05 02:17
        // SimpleGrantedAuthority 구현해서 ROLE 구분하기

         AuthUserModel authUserModel = new AuthUserModel(
                user.getUser_email(),
                user.getUser_pw(),
                user.isUser_social(),
                user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toSet())
        );

         authUserModel.setUser_name(user.getUser_name());
         authUserModel.setUser_social(user.isUser_social());

        return authUserModel;
    }
}
