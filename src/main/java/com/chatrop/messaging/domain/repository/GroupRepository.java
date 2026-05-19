package com.chatrop.messaging.domain.repository;

import com.chatrop.messaging.domain.model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Group save(Group group);
    Optional<Group> findById(String id);
    List<Group> findByMemberEmail(String email);
}
