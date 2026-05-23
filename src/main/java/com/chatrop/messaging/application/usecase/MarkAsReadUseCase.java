package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.MessageReadState;
import com.chatrop.messaging.domain.repository.MessageReadStateRepository;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;

public class MarkAsReadUseCase {
    private final MessageReadStateRepository messageReadStateRepository;
    private final UserRepository userRepository;

    public MarkAsReadUseCase(MessageReadStateRepository messageReadStateRepository, UserRepository userRepository) {
        this.messageReadStateRepository = messageReadStateRepository;
        this.userRepository = userRepository;
    }

    public void execute(String userEmail, String groupId, String peerEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String userId = currentUser.getId().toString();

        if (groupId != null && !groupId.trim().isEmpty()) {
            MessageReadState state = messageReadStateRepository.findByUserIdAndGroupId(userId, groupId)
                    .map(existing -> MessageReadState.builder()
                            .id(existing.getId())
                            .userId(existing.getUserId())
                            .groupId(existing.getGroupId())
                            .lastReadTimestamp(LocalDateTime.now())
                            .build())
                    .orElseGet(() -> MessageReadState.builder()
                            .id(UUID.randomUUID())
                            .userId(userId)
                            .groupId(groupId)
                            .lastReadTimestamp(LocalDateTime.now())
                            .build());
            messageReadStateRepository.save(state);
        } else if (peerEmail != null && !peerEmail.trim().isEmpty()) {
            User peer = userRepository.findByEmail(peerEmail)
                    .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));
            String peerId = peer.getId().toString();

            MessageReadState state = messageReadStateRepository.findByUserIdAndPeerId(userId, peerId)
                    .map(existing -> MessageReadState.builder()
                            .id(existing.getId())
                            .userId(existing.getUserId())
                            .peerId(existing.getPeerId())
                            .lastReadTimestamp(LocalDateTime.now())
                            .build())
                    .orElseGet(() -> MessageReadState.builder()
                            .id(UUID.randomUUID())
                            .userId(userId)
                            .peerId(peerId)
                            .lastReadTimestamp(LocalDateTime.now())
                            .build());
            messageReadStateRepository.save(state);
        }
    }
}
