package com.chatrop.messaging.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final String senderId;
    private final String senderEmail;
    private final String receiverId; // Puede ser null si es para un grupo
    private final String groupId;    // NUEVO: null si es un chat privado (DM)
    private final String content;
    private final LocalDateTime timestamp;

    // Constructor privado para el builder actualizado
    private Message(UUID id, String senderId, String senderEmail, String receiverId, String groupId, String content,
            LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.senderEmail = senderEmail;
        this.receiverId = receiverId;
        this.groupId = groupId;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String senderId;
        private String senderEmail;
        private String receiverId;
        private String groupId;
        private String content;
        private LocalDateTime timestamp;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder senderId(String senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder senderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        public Builder receiverId(String receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Message build() {
            return new Message(id, senderId, senderEmail, receiverId, groupId, content, timestamp);
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId='" + senderId + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
