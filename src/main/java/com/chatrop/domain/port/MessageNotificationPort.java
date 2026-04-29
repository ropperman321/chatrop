package com.chatrop.domain.port;

import com.chatrop.domain.model.Message;

public interface MessageNotificationPort {
    void notify(Message message);
}
