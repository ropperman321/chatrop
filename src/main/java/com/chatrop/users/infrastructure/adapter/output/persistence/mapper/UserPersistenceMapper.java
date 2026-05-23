package com.chatrop.users.infrastructure.adapter.output.persistence.mapper;

import com.chatrop.users.domain.model.User;
import com.chatrop.users.infrastructure.adapter.output.persistence.entity.UserEntity;

public interface UserPersistenceMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}
