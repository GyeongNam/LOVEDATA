package com.project.love_data.security.service;

import org.springframework.security.core.AuthenticationException;

public class AlreadyRegisteredEmailException extends AuthenticationException {
    private final int ERR_CODE;

    public AlreadyRegisteredEmailException(String msg, int ERR_CODE) {
        super(msg);
        this.ERR_CODE = ERR_CODE;
    }

    public AlreadyRegisteredEmailException(String msg) {
        this(msg, 100);
    }

    public int getErrCode() {
        return ERR_CODE;
    }
}
