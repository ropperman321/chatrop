package com.chatrop.users.infrastructure.adapter.output.security;

import com.chatrop.users.domain.port.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder;

    public BCryptHasher() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String hash(String plainText) {
        return encoder.encode(plainText);
    }

    @Override
    public boolean check(String plainText, String hash) {
        return encoder.matches(plainText, hash);
    }
}
