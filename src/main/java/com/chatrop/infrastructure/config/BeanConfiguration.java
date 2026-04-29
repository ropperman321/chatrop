package com.chatrop.infrastructure.config;

import com.chatrop.application.usecase.SendMessageUseCase;
import com.chatrop.domain.port.MessageNotificationPort;
import com.chatrop.domain.port.MessageRepository;
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
            MessageNotificationPort notificationPort) { // Inyectamos el nuevo adaptador
        return new SendMessageUseCase(messageRepository, notificationPort);
    }
}
