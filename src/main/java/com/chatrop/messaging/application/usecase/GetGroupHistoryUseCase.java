package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.domain.repository.GroupRepository;
import com.chatrop.messaging.domain.repository.MessageRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetGroupHistoryUseCase {

    private final MessageRepository messageRepository;
    private final GroupRepository groupRepository;

    public GetGroupHistoryUseCase(MessageRepository messageRepository, GroupRepository groupRepository) {
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
    }

    public List<Message> execute(String userEmail, String groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        
        if (group.getMemberEmails() == null || !group.getMemberEmails().contains(userEmail)) {
            throw new RuntimeException("Acceso denegado: no eres miembro de este grupo");
        }
        
        return messageRepository.findByGroupId(groupId);
    }
}
