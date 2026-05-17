package com.chatrop.users.infrastructure.adapter.output.persistence;

import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import com.chatrop.users.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.chatrop.users.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import com.chatrop.users.infrastructure.adapter.output.persistence.repository.UserJpaRepository;

import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresUserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    // Constructor manual: Evita el error TypeTag de Lombok
    public PostgresUserRepositoryAdapter(UserJpaRepository jpaRepository, UserPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @SuppressWarnings("null")
    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    // Añade este método dentro de la clase PostgresUserRepositoryAdapter
    @SuppressWarnings("null")
    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
