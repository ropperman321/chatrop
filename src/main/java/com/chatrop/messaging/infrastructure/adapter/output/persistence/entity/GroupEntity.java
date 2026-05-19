package com.chatrop.messaging.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {

    @Id
    @Column(columnDefinition = "uuid") // Consistente con el modelo inmutable Group
    private java.util.UUID id;
    
    private String name;
    
    @Column(name = "creator_id")
    private String creatorId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "user_email")
    private List<String> memberEmails;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
