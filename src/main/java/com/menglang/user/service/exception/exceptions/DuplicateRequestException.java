package com.menglang.user.service.exception.exceptions;

import com.menglang.user.service.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateRequestException extends BaseException {
   public DuplicateRequestException(String message){super(message);}

   public DuplicateRequestException(String message, String code) {
      super(message, code);
   }

   public DuplicateRequestException(String message, String code, Object object) {
      super(message, code, object);
   }

   public DuplicateRequestException(String message, String code, Object object, HttpStatus httpStatus) {
      super(message, code, object, httpStatus);
   }
}
