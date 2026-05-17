package com.chatrop.messaging.domain.port;

import java.util.UUID;

public interface UserValidatorPort {
    boolean existsById(UUID userId);
}
