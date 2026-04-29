package com.chatrop.infrastructure.adapter.output.persistence.mapper;

import com.chatrop.domain.model.Message;
import com.chatrop.infrastructure.adapter.output.persistence.MessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessagePersistenceMapper {

    /**
     * Pasa del Dominio (Puro) a la Entidad (Postgres)
     */
    MessageEntity toEntity(Message message);

    /**
     * Pasa de la Entidad (Postgres) al Dominio (Puro)
     */
    Message toDomain(MessageEntity messageEntity);
}
