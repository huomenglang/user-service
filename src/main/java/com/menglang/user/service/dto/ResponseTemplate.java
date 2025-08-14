package com.menglang.user.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResponseTemplate(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTime,
        String message,
        String code,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Object error,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Object data
) {
}
