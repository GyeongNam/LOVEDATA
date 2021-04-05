package com.project.love_data.security.converter;

import com.project.love_data.security.model.UserRole;
import org.springframework.core.convert.converter.Converter;

public class UserRoleConverter implements Converter<UserRole, String> {
    @Override
    public String convert(UserRole source) {
        return source.name();
    }
}
