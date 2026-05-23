package com.chatrop.messaging.infrastructure.adapter.output.persistence;

import com.chatrop.messaging.domain.model.MessageReadState;
import com.chatrop.messaging.domain.repository.MessageReadStateRepository;
import com.chatrop.messaging.infrastructure.adapter.output.persistence.entity.MessageReadStateEntity;
import com.chatrop.messaging.infrastructure.adapter.output.persistence.repository.JpaMessageReadStateRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PostgresMessageReadStateRepositoryAdapter implements MessageReadStateRepository {

    private final JpaMessageReadStateRepository jpaRepository;

    public PostgresMessageReadStateRepositoryAdapter(JpaMessageReadStateRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MessageReadState save(MessageReadState messageReadState) {
        return toDomain(jpaRepository.save(toEntity(messageReadState)));
    }

    @Override
    public Optional<MessageReadState> findByUserIdAndGroupId(String userId, String groupId) {
        return jpaRepository.findByUserIdAndGroupId(userId, groupId).map(this::toDomain);
    }

    @Override
    public Optional<MessageReadState> findByUserIdAndPeerId(String userId, String peerId) {
        return jpaRepository.findByUserIdAndPeerId(userId, peerId).map(this::toDomain);
    }

    private MessageReadStateEntity toEntity(MessageReadState domain) {
        MessageReadStateEntity entity = new MessageReadStateEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setGroupId(domain.getGroupId());
        entity.setPeerId(domain.getPeerId());
        entity.setLastReadTimestamp(domain.getLastReadTimestamp());
        return entity;
    }

    private MessageReadState toDomain(MessageReadStateEntity entity) {
        return MessageReadState.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .groupId(entity.getGroupId())
                .peerId(entity.getPeerId())
                .lastReadTimestamp(entity.getLastReadTimestamp())
                .build();
    }
}
