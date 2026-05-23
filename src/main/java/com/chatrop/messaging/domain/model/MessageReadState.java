package com.chatrop.messaging.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageReadState {
    private final UUID id;
    private final String userId;
    private final String groupId; // Nullable
    private final String peerId;  // Nullable
    private final LocalDateTime lastReadTimestamp;

    private MessageReadState(UUID id, String userId, String groupId, String peerId, LocalDateTime lastReadTimestamp) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.peerId = peerId;
        this.lastReadTimestamp = lastReadTimestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPeerId() {
        return peerId;
    }

    public LocalDateTime getLastReadTimestamp() {
        return lastReadTimestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String userId;
        private String groupId;
        private String peerId;
        private LocalDateTime lastReadTimestamp;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder peerId(String peerId) {
            this.peerId = peerId;
            return this;
        }

        public Builder lastReadTimestamp(LocalDateTime lastReadTimestamp) {
            this.lastReadTimestamp = lastReadTimestamp;
            return this;
        }

        public MessageReadState build() {
            return new MessageReadState(id, userId, groupId, peerId, lastReadTimestamp);
        }
    }
}
