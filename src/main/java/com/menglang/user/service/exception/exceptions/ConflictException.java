package com.menglang.user.service.exception.exceptions;

import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, String code) {
        super(message, code);
    }

    public ConflictException(String message, String code, Object object) {
        super(message, code, object);
    }

    public ConflictException(String message, String code, Object object, HttpStatus httpStatus) {
        super(message, code, object, httpStatus);
    }
}
