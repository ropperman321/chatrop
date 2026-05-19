package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.repository.GroupRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddMemberToGroupUseCase {

    private final GroupRepository groupRepository;

    public AddMemberToGroupUseCase(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group execute(String groupId, String userEmailToAdd) {
        // 1. Validar que el grupo existe
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        // 2. Validar que no esté ya dentro del grupo
        if (group.getMemberEmails().contains(userEmailToAdd)) {
            throw new RuntimeException("El usuario ya pertenece a este grupo");
        }

        // Creamos una nueva lista y un nuevo objeto Group modificado respetando la inmutabilidad
        List<String> updatedMembers = new ArrayList<>(group.getMemberEmails());
        updatedMembers.add(userEmailToAdd);

        Group updatedGroup = Group.builder()
                .id(group.getId())
                .name(group.getName())
                .creatorId(group.getCreatorId())
                .memberEmails(updatedMembers)
                .createdAt(group.getCreatedAt())
                .build();

        return groupRepository.save(updatedGroup);
    }
}
