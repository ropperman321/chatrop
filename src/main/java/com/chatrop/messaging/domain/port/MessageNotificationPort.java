package com.chatrop.messaging.domain.port;

import com.chatrop.messaging.domain.model.Message;

public interface MessageNotificationPort {
    void notify(Message message);
}
