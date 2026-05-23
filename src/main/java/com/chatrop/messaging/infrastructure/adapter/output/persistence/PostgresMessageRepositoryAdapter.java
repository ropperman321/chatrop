package com.chatrop.messaging.infrastructure.adapter.output.persistence;

import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.domain.repository.MessageRepository;
import com.chatrop.messaging.infrastructure.adapter.output.persistence.entity.MessageEntity;
import com.chatrop.messaging.infrastructure.adapter.output.persistence.repository.JpaMessageRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresMessageRepositoryAdapter implements MessageRepository {

    private final JpaMessageRepository jpaMessageRepository;

    // Ya no inyectamos MessagePersistenceMapper, el adaptador se auto-gestiona
    public PostgresMessageRepositoryAdapter(JpaMessageRepository jpaMessageRepository) {
        this.jpaMessageRepository = jpaMessageRepository;
    }

    @Override
    public Message save(Message message) {
        return toDomain(jpaMessageRepository.save(toEntity(message)));
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Message> findById(String id) {
        return jpaMessageRepository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public List<Message> findChatHistory(String user1, String user2) {
        return jpaMessageRepository.findMessagesBetweenUsers(user1, user2)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> findByGroupId(String groupId) {
        // La consulta ya viene filtrada y ordenada desde la base de datos de forma
        // ultra eficiente
        return jpaMessageRepository.findByGroupIdOrderByTimestampAsc(groupId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> findActivePeersForUser(String userId) {
        return jpaMessageRepository.findActivePeersForUser(userId);
    }

    @Override
    public long countUnreadGroupMessages(String userId, String groupId) {
        return jpaMessageRepository.countUnreadGroupMessages(userId, groupId);
    }

    @Override
    public long countUnreadDirectMessages(String userId, String peerId) {
        return jpaMessageRepository.countUnreadDirectMessages(userId, peerId);
    }

    private MessageEntity toEntity(Message domain) {
        MessageEntity entity = new MessageEntity();
        entity.setId(domain.getId());
        entity.setSenderId(domain.getSenderId());
        entity.setSenderEmail(domain.getSenderEmail());
        entity.setReceiverId(domain.getReceiverId());
        entity.setReceiverEmail(domain.getReceiverEmail());
        entity.setGroupId(domain.getGroupId());
        entity.setContent(domain.getContent());
        entity.setTimestamp(domain.getTimestamp());
        return entity;
    }

    private Message toDomain(MessageEntity entity) {
        return Message.builder()
                .id(entity.getId())
                .senderId(entity.getSenderId())
                .senderEmail(entity.getSenderEmail())
                .receiverId(entity.getReceiverId())
                .receiverEmail(entity.getReceiverEmail())
                .groupId(entity.getGroupId())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
