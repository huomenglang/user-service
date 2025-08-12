package com.menglang.user.service.exception.exceptions;


import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
    public ForbiddenException(String message){super(message);}

    public ForbiddenException(String message, String code) {
        super(message, code);
    }

    public ForbiddenException(String message, String code, Object object) {
        super(message, code, object);
    }

    public ForbiddenException(String message, String code, Object object, HttpStatus httpStatus) {
        super(message, code, object, httpStatus);
    }
}
