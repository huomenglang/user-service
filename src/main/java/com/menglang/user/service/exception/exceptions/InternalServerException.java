package com.menglang.user.service.exception.exceptions;


import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InternalServerException extends BaseException {
    public InternalServerException(String message){super(message);}

    public InternalServerException(String message, String code) {
        super(message, code);
    }

    public InternalServerException(String message, String code, Object object) {
        super(message, code, object);
    }

    public InternalServerException(String message, String code, Object object, HttpStatus httpStatus) {
        super(message, code, object, httpStatus);
    }
}
