package com.chatrop.messaging.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "message_read_states")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageReadStateEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "group_id", nullable = true)
    private String groupId;

    @Column(name = "peer_id", nullable = true)
    private String peerId;

    @Column(name = "last_read_timestamp", nullable = false)
    private LocalDateTime lastReadTimestamp;
}
