package com.menglang.user.service.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfigProperties {
    private String url;
    private String  header;
    private String prefix;
    private Long expire;
    private String secret;
    private String defaultPassword;
}
