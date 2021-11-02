package com.project.love_data.security.service;

import com.project.love_data.repository.UserRepository;
import com.project.love_data.model.user.User;
import com.project.love_data.security.model.AuthUserModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import com.project.love_data.security.service.AlreadyRegisteredEmailException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException, AlreadyRegisteredEmailException {
        log.info("UserDetailsService loadUserByUsername " + username);

        // non uniqe 리턴 예외 가능 AND 삭제되지 않은 계정 정보만 찾아오기
        Optional<User> result = userRepository.findLiveUserByEmail_Privilege(username);
        // 모든 계정 정보 찾아오기
        Optional<User> delResult = userRepository.findUserByEmail_Privilege(username);

        if (!result.isPresent() && delResult.isPresent()) {
            log.warn("This user accounts already deleted. Can't signup with this email");
            throw new AlreadyRegisteredEmailException("Can't proceed signup with this email. Please try another email");
        }
//        log.info("어스 리설트 확인" + result);
        // DB에 저장된 유저 정보가 없을 때
        if (!result.isPresent()) {
//            throw new UsernameNotFoundException("Check Email");
            log.info("Login Failed : " + username);
            throw new BadCredentialsException("Check Email and Password");
        }

        User user = result.get();

        log.debug("UserDetailsService user info : " + user.toString());
        log.debug("UserDetailsService userRole(SET) : " + user.getRoleSet());
        log.debug("UserDetailsService userRole : " + user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));

         AuthUserModel authUserModel = new AuthUserModel(
                user.getUser_email(),
                user.getUser_pw(),
                user.getProfile_pic(),
                user.isUser_social(),
                user.getSocial_info(),
                user.getSocial_id(),
                user.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toSet())
        );

         // 유저 패스워드 설정시 로그에 출력됨
        authUserModel.setUser_regDate(user.getRegDate());
        authUserModel.setUser_no(user.getUser_no());
        authUserModel.setUser_pw(user.getUser_pw());
        authUserModel.setUser_name(user.getUser_name());
        authUserModel.setUser_birth(user.getUser_birth());
        authUserModel.setUser_no(user.getUser_no());
        authUserModel.setUser_Activation(user.isUser_Activation());
        authUserModel.setUser_sex(user.isUser_sex());
        authUserModel.setUser_nic(user.getUser_nic());
        authUserModel.setUser_emil_re(user.isUser_email_re());
        authUserModel.setUser_phone(user.getUser_phone());
        authUserModel.setSocial_info(user.getSocial_info());
        authUserModel.setUser_profilePic(user.getProfile_pic());
//        log.info("UserDetailService_authUserModel : " + authUserModel);
        log.info("Login Successful  :  " + authUserModel.getUser_email());
        return authUserModel;
    }
}
