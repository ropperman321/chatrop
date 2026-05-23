package com.chatrop.messaging.infrastructure.adapter.output.persistence.repository;

import com.chatrop.messaging.infrastructure.adapter.output.persistence.entity.MessageReadStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaMessageReadStateRepository extends JpaRepository<MessageReadStateEntity, UUID> {
    Optional<MessageReadStateEntity> findByUserIdAndGroupId(String userId, String groupId);
    Optional<MessageReadStateEntity> findByUserIdAndPeerId(String userId, String peerId);
}
