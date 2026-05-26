package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.repository.GroupRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveGroupUseCase {

    private final GroupRepository groupRepository;

    public LeaveGroupUseCase(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Removes a user from the group members.
     *
     * @param groupId       the identifier of the group
     * @param userEmail     the email of the user leaving the group
     * @return the updated Group entity
     */
    public Group execute(String groupId, String userEmail) {
        // 1. Verify group exists
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        // 2. Verify user is a member
        if (!group.getMemberEmails().contains(userEmail)) {
            throw new RuntimeException("El usuario no pertenece a este grupo");
        }

        // 3. Remove user from members list
        List<String> updatedMembers = group.getMemberEmails()
                .stream()
                .filter(email -> !email.equals(userEmail))
                .collect(Collectors.toList());

        // 4. Build updated group (immutability pattern)
        Group updatedGroup = Group.builder()
                .id(group.getId())
                .name(group.getName())
                .creatorId(group.getCreatorId())
                .memberEmails(updatedMembers)
                .createdAt(group.getCreatedAt())
                .build();

        // 5. Persist changes
        return groupRepository.save(updatedGroup);
    }
}
