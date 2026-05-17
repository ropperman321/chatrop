package com.chatrop.users.application.usecase;

import com.chatrop.users.domain.port.PasswordHasher;
import com.chatrop.users.domain.port.TokenService;
import com.chatrop.users.domain.port.UserRepository;

public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public LoginUserUseCase(UserRepository userRepository, PasswordHasher passwordHasher, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    public String execute(String email, String plainPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordHasher.check(plainPassword, user.getPasswordHash()))
                .map(tokenService::generateToken) // <--- Generamos el pasaporte
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}
