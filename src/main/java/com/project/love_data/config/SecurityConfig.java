package com.project.love_data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//	  http.formLogin().loginPage("/login");

      http.authorizeRequests()
              .antMatchers("/sample/member").hasRole("USER")
              .antMatchers("/sample/all").permitAll();
      // csrf 토큰 비활성화
//        http.csrf().disable();
//      super.configure(http);
      //로그인 화면 비활성화
      http.httpBasic().disable();
  }

  // 패스워드 암호화를 위한 함수
  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }
}

