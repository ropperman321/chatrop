package com.chatrop.messaging.domain.repository;

import java.util.List;
import java.util.Optional;

import com.chatrop.messaging.domain.model.Message;

public interface MessageRepository {
    // Guardar un mensaje
    Message save(Message message);

    // Buscar un mensaje por su ID
    Optional<Message> findById(String id);

    // Obtener el historial entre dos usuarios
    List<Message> findChatHistory(String user1, String user2);

    // Dentro de MessageRepository.java
    java.util.List<Message> findByGroupId(String groupId);
}
