package com.chatrop.infrastructure.config;

import com.chatrop.messaging.application.usecase.GetChatHistoryUseCase;
import com.chatrop.messaging.application.usecase.SendMessageUseCase;
import com.chatrop.messaging.domain.repository.MessageRepository;
import com.chatrop.users.application.usecase.LoginUserUseCase;
import com.chatrop.users.application.usecase.RegisterUserUseCase;
import com.chatrop.users.domain.port.PasswordHasher;
import com.chatrop.users.domain.port.TokenService;
import com.chatrop.users.domain.port.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    /**
     * Aquí le decimos a Spring: "Cuando alguien necesite un SendMessageUseCase,
     * crea uno nuevo y dale el MessageRepository que ya tienes en tu contenedor".
     */
    @Bean
    public SendMessageUseCase sendMessageUseCase(
            MessageRepository messageRepository,
            UserRepository userRepository) { // Inyectamos el repositorio de usuarios
        return new SendMessageUseCase(messageRepository, userRepository);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            PasswordHasher passwordHasher) {
        return new RegisterUserUseCase(userRepository, passwordHasher);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            TokenService tokenService) { // <--- Añadimos el tokenService como parámetro

        // 2. Pasamos los tres parámetros al constructor
        return new LoginUserUseCase(userRepository, passwordHasher, tokenService);
    }

    @Bean
    public GetChatHistoryUseCase getChatHistoryUseCase(
            MessageRepository messageRepository,
            UserRepository userRepository) {
        return new GetChatHistoryUseCase(messageRepository, userRepository);
    }

    @Bean
    public com.chatrop.messaging.application.usecase.GetGroupHistoryUseCase getGroupHistoryUseCase(
            MessageRepository messageRepository) {
        return new com.chatrop.messaging.application.usecase.GetGroupHistoryUseCase(messageRepository);
    }
}
