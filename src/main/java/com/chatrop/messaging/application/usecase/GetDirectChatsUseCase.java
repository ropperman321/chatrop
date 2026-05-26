package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.repository.MessageRepository;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GetDirectChatsUseCase {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public GetDirectChatsUseCase(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<DirectChat> execute(String userEmail) {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(u -> !u.getEmail().equalsIgnoreCase(userEmail))
                .map(u -> new DirectChat(u.getId().toString(), u.getEmail()))
                .collect(Collectors.toList());
    }

    public static class DirectChat {
        private final String userId;
        private final String email;

        public DirectChat(String userId, String email) {
            this.userId = userId;
            this.email = email;
        }

        public String getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }
    }
}
