package com.chatrop.infrastructure.adapter.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    @Id
    @Column(columnDefinition = "uuid") // Esto fuerza a Postgres a usar el tipo UUID
    private java.util.UUID id; // Cambia String por UUID si estaba en String
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;
}
