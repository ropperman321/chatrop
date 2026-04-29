package com.chatrop.domain.port;

import com.chatrop.domain.model.Message;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    // Guardar un mensaje
    Message save(Message message);

    // Buscar un mensaje por su ID
    Optional<Message> findById(String id);

    // Obtener el historial entre dos usuarios
    List<Message> findChatHistory(String user1, String user2);
}
