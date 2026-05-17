package com.chatrop.users.infrastructure.adapter.input.rest.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String password;
}
