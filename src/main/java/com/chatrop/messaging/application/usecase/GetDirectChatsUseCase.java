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
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String userId = currentUser.getId().toString();

        List<Object[]> peers = messageRepository.findActivePeersForUser(userId);

        return peers.stream()
                .map(row -> {
                    String peerId = (String) row[0];
                    String peerEmail = (String) row[1];
                    if (peerId == null || peerEmail == null) {
                        return null;
                    }
                    return new DirectChat(peerId, peerEmail);
                })
                .filter(Objects::nonNull)
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
