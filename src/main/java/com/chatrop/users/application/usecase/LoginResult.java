package com.chatrop.users.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {
    private final String token;
    private final String userId;
}
