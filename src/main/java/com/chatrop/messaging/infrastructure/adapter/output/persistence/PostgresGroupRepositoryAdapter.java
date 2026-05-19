package com.chatrop.messaging.infrastructure.adapter.output.persistence;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.repository.GroupRepository;
import com.chatrop.messaging.infrastructure.adapter.output.persistence.entity.GroupEntity;
import com.chatrop.messaging.infrastructure.adapter.output.persistence.repository.JpaGroupRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresGroupRepositoryAdapter implements GroupRepository {

    private final JpaGroupRepository jpaGroupRepository;

    public PostgresGroupRepositoryAdapter(JpaGroupRepository jpaGroupRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
    }

    @SuppressWarnings("null")
    @Override
    public Group save(Group group) {
        GroupEntity entity = toEntity(group);
        GroupEntity savedEntity = jpaGroupRepository.save(entity);
        return toDomain(savedEntity);
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Group> findById(String id) {
        return jpaGroupRepository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public List<Group> findByMemberEmail(String email) {
        return jpaGroupRepository.findByMemberEmailsContaining(email)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private GroupEntity toEntity(Group domain) {
        GroupEntity entity = new GroupEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setCreatorId(domain.getCreatorId());
        entity.setMemberEmails(domain.getMemberEmails());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    private Group toDomain(GroupEntity entity) {
        return Group.builder()
                .id(entity.getId())
                .name(entity.getName())
                .creatorId(entity.getCreatorId())
                .memberEmails(entity.getMemberEmails())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
