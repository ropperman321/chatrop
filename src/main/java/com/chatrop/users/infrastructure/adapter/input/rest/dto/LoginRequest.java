package com.chatrop.users.infrastructure.adapter.input.rest.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
