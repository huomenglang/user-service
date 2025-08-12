package com.menglang.user.service.exception.exceptions;


import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message){
        super(message);
    }

    public UnauthorizedException(String message, String code) {
        super(message, code);
    }

    public UnauthorizedException(String message, String code, Object object) {
        super(message, code, object);
    }

    public UnauthorizedException(String message, String code, Object object, HttpStatus httpStatus) {
        super(message, code, object, httpStatus);
    }
}
