package com.chatrop.messaging.domain.repository;

import com.chatrop.messaging.domain.model.MessageReadState;
import java.util.Optional;

public interface MessageReadStateRepository {
    MessageReadState save(MessageReadState messageReadState);
    Optional<MessageReadState> findByUserIdAndGroupId(String userId, String groupId);
    Optional<MessageReadState> findByUserIdAndPeerId(String userId, String peerId);
}
