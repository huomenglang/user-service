package com.menglang.user.service.exception.exceptions;

import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends BaseException {
   public BadRequestException(String message){super(message);}

   public BadRequestException(String message, String code) {
      super(message, code);
   }

   public BadRequestException(String message, String code, Object object) {
      super(message, code, object);
   }

   public BadRequestException(String message, String code, Object object, HttpStatus httpStatus) {
      super(message, code, object, httpStatus);
   }
}
