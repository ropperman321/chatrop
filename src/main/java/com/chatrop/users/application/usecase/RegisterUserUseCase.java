package com.chatrop.users.application.usecase;

import java.util.UUID;

import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.PasswordHasher;
import com.chatrop.users.domain.port.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher; // Interfaz que definiremos

    public User execute(String email, String plainPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .passwordHash(passwordHasher.hash(plainPassword))
                .build();

        return userRepository.save(user);
    }
}
