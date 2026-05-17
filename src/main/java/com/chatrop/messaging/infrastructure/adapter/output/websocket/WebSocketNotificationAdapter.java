package com.chatrop.messaging.infrastructure.adapter.output.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.domain.port.MessageNotificationPort;

@Component
public class WebSocketNotificationAdapter implements MessageNotificationPort {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notify(Message message) {
        String destination = "/topic/messages/" + message.getReceiverId();
        this.messagingTemplate.convertAndSend(destination, message);
    }
}
