package com.menglang.user.service.configs;

import com.menglang.user.service.entity.audit.AuditAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig  {

    @Bean
    public AuditorAware<String> AuditAwareBean(){
        return new AuditAwareImpl();
    }
}
