package com.chatrop.infrastructure.adapter;

import com.chatrop.messaging.domain.port.UserValidatorPort;
import com.chatrop.users.domain.port.UserRepository;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UserValidationAdapter implements UserValidatorPort {

    private final UserRepository userRepository;

    public UserValidationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsById(UUID userId) {
        // Ahora usamos el método findById real
        return userRepository.findById(userId).isPresent();
    }
}
