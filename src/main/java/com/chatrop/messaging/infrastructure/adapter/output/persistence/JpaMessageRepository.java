package com.chatrop.messaging.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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
}
