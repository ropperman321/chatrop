package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.domain.port.MessageRepository;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import java.util.List;

public class GetChatHistoryUseCase {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public GetChatHistoryUseCase(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<Message> execute(String userEmail, String otherUserId) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return messageRepository.findChatHistory(currentUser.getId().toString(), otherUserId);
    }
}
