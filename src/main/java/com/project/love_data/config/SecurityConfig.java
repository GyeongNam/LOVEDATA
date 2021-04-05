package com.project.love_data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
      http.authorizeRequests()
              .antMatchers("/sample/member").hasRole("USER")
              .antMatchers("/sample/all").permitAll();
      // csrf 토큰 비활성화
//        http.csrf().disable();
//      super.configure(http);

      //로그인 화면 비활성화
      http.httpBasic().disable();
      // 기본 로그인 화면
      http.formLogin();
      // TODO 기본 로그인 화면에서 커스텀 로그인 화면으로 바꾸기
      //	  http.formLogin().loginPage("/login");
  }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("mon0mon").password(passwordEncoder().encode("1111")).roles("USER");
//    }

    // 패스워드 암호화를 위한 함수
  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }
}

