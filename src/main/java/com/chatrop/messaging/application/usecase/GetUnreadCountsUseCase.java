package com.chatrop.messaging.application.usecase;

import com.chatrop.messaging.domain.model.Group;
import com.chatrop.messaging.domain.repository.GroupRepository;
import com.chatrop.messaging.domain.repository.MessageRepository;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.domain.port.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUnreadCountsUseCase {
    private final MessageRepository messageRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GetUnreadCountsUseCase(MessageRepository messageRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public UnreadCounts execute(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String userId = currentUser.getId().toString();

        Map<String, Long> groupsCounts = new HashMap<>();
        List<Group> userGroups = groupRepository.findByMemberEmail(userEmail);
        for (Group group : userGroups) {
            long count = messageRepository.countUnreadGroupMessages(userId, group.getId().toString());
            groupsCounts.put(group.getId().toString(), count);
        }

        Map<String, Long> directsCounts = new HashMap<>();
        List<Object[]> peers = messageRepository.findActivePeersForUser(userId);
        for (Object[] row : peers) {
            String peerId = (String) row[0];
            String peerEmail = (String) row[1];
            if (peerId != null && peerEmail != null) {
                long count = messageRepository.countUnreadDirectMessages(userId, peerId);
                directsCounts.put(peerEmail, count);
            }
        }

        return new UnreadCounts(groupsCounts, directsCounts);
    }

    public static class UnreadCounts {
        private final Map<String, Long> groups;
        private final Map<String, Long> directs;

        public UnreadCounts(Map<String, Long> groups, Map<String, Long> directs) {
            this.groups = groups;
            this.directs = directs;
        }

        public Map<String, Long> getGroups() {
            return groups;
        }

        public Map<String, Long> getDirects() {
            return directs;
        }
    }
}
