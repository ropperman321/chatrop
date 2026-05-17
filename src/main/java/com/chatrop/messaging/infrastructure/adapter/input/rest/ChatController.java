package com.chatrop.messaging.infrastructure.adapter.input.rest;

import com.chatrop.messaging.application.usecase.GetChatHistoryUseCase;
import com.chatrop.messaging.application.usecase.SendMessageUseCase;
import com.chatrop.messaging.domain.model.Message;
import com.chatrop.messaging.infrastructure.adapter.input.rest.dto.MessageRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class ChatController {

    private final SendMessageUseCase sendMessageUseCase;
    private final GetChatHistoryUseCase getChatHistoryUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SendMessageUseCase sendMessageUseCase, GetChatHistoryUseCase getChatHistoryUseCase,
            SimpMessagingTemplate messagingTemplate) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.getChatHistoryUseCase = getChatHistoryUseCase;
        this.messagingTemplate = messagingTemplate;
    }

    @SuppressWarnings("null")
    @PostMapping("/send")
    public ResponseEntity<Message> send(@RequestBody MessageRequest request) {
        // EXTRAEMOS EL EMAIL DEL TOKEN JWT DE FORMA SEGURA
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Message sentMessage = sendMessageUseCase.execute(
                email,
                request.getReceiverId(),
                request.getContent());

        messagingTemplate.convertAndSend("/topic/messages", sentMessage);

        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/history/{receiverId}")
    public ResponseEntity<List<Message>> getHistory(@PathVariable String receiverId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(getChatHistoryUseCase.execute(email, receiverId));
    }
}
