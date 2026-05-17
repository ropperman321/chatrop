package com.chatrop.users.infrastructure.adapter.output.persistence.mapper;

import com.chatrop.users.domain.model.User;
import com.chatrop.users.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}
