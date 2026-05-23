package com.chatrop.messaging.infrastructure.adapter.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chatrop.messaging.infrastructure.adapter.output.persistence.entity.MessageEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaMessageRepository extends JpaRepository<MessageEntity, UUID> {
    // @Query("SELECT m FROM MessageEntity m WHERE (m.senderId = ?1 AND m.receiverId
    // = ?2) OR (m.senderId = ?2 AND m.receiverId = ?1) ORDER BY m.timestamp ASC")
    // List<MessageEntity> findMessagesBetweenUsers(String u1, String u2);

    @Query("SELECT m FROM MessageEntity m WHERE " +
            "(m.senderId = :u1 AND m.receiverId = :u2) OR " +
            "(m.senderId = :u2 AND m.receiverId = :u1) " +
            "ORDER BY m.timestamp ASC")
    List<MessageEntity> findMessagesBetweenUsers(@Param("u1") String u1, @Param("u2") String u2);

    // Dentro de JpaMessageRepository.java
    java.util.List<MessageEntity> findByGroupIdOrderByTimestampAsc(String groupId);

    @Query("SELECT DISTINCT " +
           "CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END, " +
           "CASE WHEN m.senderId = :userId THEN m.receiverEmail ELSE m.senderEmail END " +
           "FROM MessageEntity m WHERE (m.senderId = :userId OR m.receiverId = :userId) AND m.groupId IS NULL")
    List<Object[]> findActivePeersForUser(@Param("userId") String userId);

    @Query("SELECT COUNT(m) FROM MessageEntity m WHERE m.groupId = :groupId AND m.senderId <> :userId AND " +
           "(m.timestamp > (SELECT r.lastReadTimestamp FROM MessageReadStateEntity r WHERE r.userId = :userId AND r.groupId = :groupId) " +
           "OR NOT EXISTS (SELECT r FROM MessageReadStateEntity r WHERE r.userId = :userId AND r.groupId = :groupId))")
    long countUnreadGroupMessages(@Param("userId") String userId, @Param("groupId") String groupId);

    @Query("SELECT COUNT(m) FROM MessageEntity m WHERE m.groupId IS NULL AND m.senderId = :peerId AND m.receiverId = :userId AND " +
           "(m.timestamp > (SELECT r.lastReadTimestamp FROM MessageReadStateEntity r WHERE r.userId = :userId AND r.peerId = :peerId) " +
           "OR NOT EXISTS (SELECT r FROM MessageReadStateEntity r WHERE r.userId = :userId AND r.peerId = :peerId))")
    long countUnreadDirectMessages(@Param("userId") String userId, @Param("peerId") String peerId);
}
