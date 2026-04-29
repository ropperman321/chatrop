package com.chatrop.application.usecase;

import com.chatrop.domain.model.Message;
import com.chatrop.domain.port.MessageNotificationPort;
import com.chatrop.domain.port.MessageRepository;
import java.time.LocalDateTime;
import java.util.UUID;

public class SendMessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageNotificationPort notificationPort;

    public SendMessageUseCase(MessageRepository messageRepository, MessageNotificationPort notificationPort) {
        this.messageRepository = messageRepository;
        this.notificationPort = notificationPort;
    }

    public Message execute(String senderId, String receiverId, String content) {
        // (Validaciones igual que antes...)

        Message message = Message.builder()
                .id(UUID.randomUUID())
                .senderId(senderId)
                .receiverId(receiverId)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        // 1. Guardar en base de datos
        Message savedMessage = messageRepository.save(message);

        // 2. Notificar en tiempo real
        notificationPort.notify(savedMessage);

        return savedMessage;
    }

}
