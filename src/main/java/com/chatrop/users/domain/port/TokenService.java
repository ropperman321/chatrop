package com.chatrop.users.domain.port;

import com.chatrop.users.domain.model.User;

public interface TokenService {
    String generateToken(User user);
    String getEmailFromToken(String token);
    boolean validateToken(String token);
}
