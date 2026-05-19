package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.repository.GroupRepository;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreateGroupUseCase {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository; // Necesario para buscar el UUID del creador por su email

    public CreateGroupUseCase(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group execute(String groupName, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new RuntimeException("Creador no encontrado"));

        // El creador es automáticamente el primer miembro del grupo
        List<String> members = new ArrayList<>();
        members.add(creatorEmail);

        Group group = Group.builder()
                .id(UUID.randomUUID()) // Generamos UUID nativo para el nuevo grupo
                .name(groupName)
                .creatorId(creator.getId().toString())
                .memberEmails(members)
                .createdAt(LocalDateTime.now())
                .build();

        return groupRepository.save(group);
    }
}
