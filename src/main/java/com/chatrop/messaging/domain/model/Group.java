package com.chatrop.messaging.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Group {
    private final UUID id;
    private final String name;
    private final String creatorId;
    private final List<String> memberEmails;
    private final LocalDateTime createdAt;

    // Constructor privado para el builder
    private Group(UUID id, String name, String creatorId, List<String> memberEmails, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.creatorId = creatorId;
        this.memberEmails = memberEmails;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<String> getMemberEmails() {
        return memberEmails;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String creatorId;
        private List<String> memberEmails;
        private LocalDateTime createdAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder creatorId(String creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public Builder memberEmails(List<String> memberEmails) {
            this.memberEmails = memberEmails;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Group build() {
            return new Group(id, name, creatorId, memberEmails, createdAt);
        }
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", memberEmails=" + memberEmails +
                ", createdAt=" + createdAt +
                '}';
    }
}
