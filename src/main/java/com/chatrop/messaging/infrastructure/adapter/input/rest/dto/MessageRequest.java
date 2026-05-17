package com.chatrop.messaging.infrastructure.adapter.input.rest.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String receiverId;
    private String content;
}
