package com.chatrop.users.domain.port;

import com.chatrop.users.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id); // <--- Nuevo método
}
