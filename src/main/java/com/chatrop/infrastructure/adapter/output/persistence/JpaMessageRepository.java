package com.chatrop.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaMessageRepository extends JpaRepository<MessageEntity, UUID> {
    @Query("SELECT m FROM MessageEntity m WHERE (m.senderId = ?1 AND m.receiverId = ?2) OR (m.senderId = ?2 AND m.receiverId = ?1) ORDER BY m.timestamp ASC")
    List<MessageEntity> findMessagesBetweenUsers(String u1, String u2);
}
