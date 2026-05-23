package com.chatrop.messaging.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    @Column(columnDefinition = "uuid") // Fuerza a Postgres a usar el tipo UUID nativo
    private java.util.UUID id;

    private String senderId;
    private String senderEmail;

    @Column(nullable = true) // Puede ser null si el mensaje va a un grupo
    private String receiverId;

    @Column(nullable = true) // Puede ser null si el mensaje va a un grupo o si no se especificó
    private String receiverEmail;

    @Column(nullable = true) // Almacena el ID del grupo (null si es un chat privado)
    private String groupId;

    private String content;
    private LocalDateTime timestamp;
}
