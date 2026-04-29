package com.chatrop.infrastructure.adapter.output.websocket;

import com.chatrop.domain.model.Message;
import com.chatrop.domain.port.MessageNotificationPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

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
