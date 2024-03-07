package com.pe.nttdata.config;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Value("${user.backend}")
    private String usuario;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(usuario);
    }

}