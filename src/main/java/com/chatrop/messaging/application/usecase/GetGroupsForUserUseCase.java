package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.repository.GroupRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetGroupsForUserUseCase {

    private final GroupRepository groupRepository;

    public GetGroupsForUserUseCase(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> execute(String userEmail) {
        return groupRepository.findByMemberEmail(userEmail);
    }
}
