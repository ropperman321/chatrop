package com.chatrop.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final LocalDateTime timestamp;

    // Constructor privado para el builder
    private Message(UUID id, String senderId, String receiverId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
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

    public String getReceiverId() {
        return receiverId;
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
        private String receiverId;
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

        public Builder receiverId(String receiverId) {
            this.receiverId = receiverId;
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
            return new Message(id, senderId, receiverId, content, timestamp);
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
