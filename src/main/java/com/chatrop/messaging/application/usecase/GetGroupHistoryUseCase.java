package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.domain.repository.MessageRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetGroupHistoryUseCase {

    private final MessageRepository messageRepository;

    public GetGroupHistoryUseCase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> execute(String groupId) {
        // Cero filtros en memoria. Rendimiento O(1) para el servidor Java.
        return messageRepository.findByGroupId(groupId);
    }
}
