package com.chatrop.messaging.infrastructure.adapter.output.persistence.repository;

import com.chatrop.messaging.infrastructure.adapter.output.persistence.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaGroupRepository extends JpaRepository<GroupEntity, UUID> {
    List<GroupEntity> findByMemberEmailsContaining(String email);
}
