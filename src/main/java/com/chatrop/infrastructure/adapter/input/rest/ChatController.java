package com.chatrop.infrastructure.adapter.input.rest;

import com.chatrop.application.usecase.SendMessageUseCase;
import com.chatrop.domain.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor // Lombok genera el constructor para inyectar SendMessageUseCase
public class ChatController {

    private final SendMessageUseCase sendMessageUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message sendMessage(@RequestBody MessageRequest request) {
        // Llamamos al caso de uso (la lógica de aplicación)
        return sendMessageUseCase.execute(
                request.senderId(),
                request.receiverId(),
                request.content());
    }
}

/**
 * Usamos un 'record' de Java 21 para el DTO.
 * Es una forma súper limpia de definir clases de solo datos.
 */
record MessageRequest(String senderId, String receiverId, String content) {
}
