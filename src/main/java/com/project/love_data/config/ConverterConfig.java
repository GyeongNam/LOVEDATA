package com.project.love_data.config;

import com.project.love_data.security.converter.UserRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    @Bean
    public UserRoleConverter userRoleConverter() {
        return new UserRoleConverter();
    }
}
