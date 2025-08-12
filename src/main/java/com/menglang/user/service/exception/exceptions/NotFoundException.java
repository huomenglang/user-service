package com.menglang.user.service.exception.exceptions;

import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(String message){super(message);}

    public NotFoundException(String message, String code) {
        super(message, code);
    }

    public NotFoundException(String message, String code, Object object) {
        super(message, code, object);
    }

    public NotFoundException(String message, String code, Object object, HttpStatus httpStatus) {
        super(message, code, object, httpStatus);
    }
}
