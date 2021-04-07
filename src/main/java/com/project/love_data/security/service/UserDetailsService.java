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

        Optional<User> result = userRepository.findUserByEmail_Privilege(username);

        // DB에 저장된 유저 정보가 없을 때
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email");
        }

        User user = result.get();

        log.debug("UserDetailsService user info : " + user.toString());
        log.debug("UserDetailsService userRole(SET) : " + user.getRoleSet());
        log.debug("UserDetailsService userRole : " + user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));

         AuthUserModel authUserModel = new AuthUserModel(
                user.getUser_email(),
                user.getUser_pw(),
                user.isUser_social(),
                user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toSet())
        );

         // 유저 패스워드 설정시 로그에 출력됨
         authUserModel.setUser_pw(user.getUser_pw());
        authUserModel.setUser_name(user.getUser_name());
        authUserModel.setUser_birth(user.getUser_birth());
        authUserModel.setUser_no(user.getUser_no());
        authUserModel.setUser_Activation(user.getUser_Activation());
        authUserModel.setUser_gen(user.getUser_gen());
        authUserModel.setUser_nic(user.getUser_nic());
        authUserModel.setUser_time(user.getUser_time());
        authUserModel.setUser_emil_re(user.getUser_emil_re());
        authUserModel.setUser_phone(user.getUser_phone());

        log.info("UserDetailService_authUserModel : " + authUserModel);

        return authUserModel;
    }
}
