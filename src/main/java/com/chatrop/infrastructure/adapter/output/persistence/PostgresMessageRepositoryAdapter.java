package com.chatrop.infrastructure.adapter.output.persistence;

import com.chatrop.domain.model.Message;
import com.chatrop.domain.port.MessageRepository;
import com.chatrop.infrastructure.adapter.output.persistence.mapper.MessagePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostgresMessageRepositoryAdapter implements MessageRepository {

    private final JpaMessageRepository jpaRepository;
    private final MessagePersistenceMapper mapper;

    @Override
    public Message save(Message message) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(message)));
    }

    @Override
    public Optional<Message> findById(String id) {
        return jpaRepository.findById(UUID.fromString(id)).map(mapper::toDomain);
    }

    @Override
    public List<Message> findChatHistory(String user1, String user2) {
        return jpaRepository.findMessagesBetweenUsers(user1, user2)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
