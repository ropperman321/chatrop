package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.domain.repository.MessageRepository;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;

public class SendMessageUseCase {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public SendMessageUseCase(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message execute(String senderEmail, String receiverId, String groupId, String content) {
        // 1. Buscamos al emisor real por el email del token
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado"));

        String resolvedReceiverId = null;
        String resolvedReceiverEmail = null;
        if (receiverId != null && !receiverId.trim().isEmpty() && (groupId == null || groupId.trim().isEmpty())) {
            User receiver = userRepository.findByEmail(receiverId)
                    .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));
            resolvedReceiverId = receiver.getId().toString();
            resolvedReceiverEmail = receiver.getEmail();
        }

        // 2. Construimos el mensaje con el ID real del emisor
        Message message = Message.builder()
                .id(UUID.randomUUID())
                .senderId(sender.getId().toString()) // Su UUID real de la DB
                .senderEmail(senderEmail) // Agregamos el email del sender
                .receiverId(resolvedReceiverId)
                .receiverEmail(resolvedReceiverEmail)
                .groupId(groupId)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }
}
