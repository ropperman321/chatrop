package com.chatrop.messaging.infrastructure.adapter.input.rest;

import com.chatrop.messaging.application.usecase.GetChatHistoryUseCase;
import com.chatrop.messaging.application.usecase.GetGroupHistoryUseCase;
import com.chatrop.messaging.application.usecase.SendMessageUseCase;
import com.chatrop.messaging.application.usecase.MarkAsReadUseCase;
import com.chatrop.messaging.application.usecase.GetUnreadCountsUseCase;
import com.chatrop.messaging.application.usecase.GetDirectChatsUseCase;
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
    private final GetGroupHistoryUseCase getGroupHistoryUseCase;
    private final MarkAsReadUseCase markAsReadUseCase;
    private final GetUnreadCountsUseCase getUnreadCountsUseCase;
    private final GetDirectChatsUseCase getDirectChatsUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SendMessageUseCase sendMessageUseCase, 
            GetChatHistoryUseCase getChatHistoryUseCase,
            GetGroupHistoryUseCase getGroupHistoryUseCase,
            MarkAsReadUseCase markAsReadUseCase,
            GetUnreadCountsUseCase getUnreadCountsUseCase,
            GetDirectChatsUseCase getDirectChatsUseCase,
            SimpMessagingTemplate messagingTemplate) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.getChatHistoryUseCase = getChatHistoryUseCase;
        this.getGroupHistoryUseCase = getGroupHistoryUseCase;
        this.markAsReadUseCase = markAsReadUseCase;
        this.getUnreadCountsUseCase = getUnreadCountsUseCase;
        this.getDirectChatsUseCase = getDirectChatsUseCase;
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
                request.getGroupId(),
                request.getContent());

        // Lógica de topics dinámicos
        if (sentMessage.getGroupId() != null && !sentMessage.getGroupId().isEmpty()) {
            // Es un mensaje de grupo
            messagingTemplate.convertAndSend("/topic/messages/group/" + sentMessage.getGroupId(), sentMessage);
        } else {
            // Es un mensaje privado (DM)
            // Enviamos al receptor
            messagingTemplate.convertAndSend("/topic/messages/" + sentMessage.getReceiverId(), sentMessage);
            // También enviamos al emisor (para sincronización entre sus dispositivos)
            messagingTemplate.convertAndSend("/topic/messages/" + sentMessage.getSenderId(), sentMessage);
        }

        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/history/{receiverId}")
    public ResponseEntity<List<Message>> getHistory(@PathVariable String receiverId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Auto-marcar como leído al cargar historial de DM (receiverId es el email en este endpoint)
        markAsReadUseCase.execute(email, null, receiverId);
        
        return ResponseEntity.ok(getChatHistoryUseCase.execute(email, receiverId));
    }

    @GetMapping("/history/group/{groupId}")
    public ResponseEntity<List<Message>> getGroupHistory(@PathVariable String groupId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Auto-marcar como leído al cargar historial de grupo
        markAsReadUseCase.execute(email, groupId, null);
        
        return ResponseEntity.ok(getGroupHistoryUseCase.execute(groupId));
    }

    @PostMapping("/read")
    public ResponseEntity<Void> markAsRead(@RequestBody ReadRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        markAsReadUseCase.execute(email, request.getGroupId(), request.getPeerEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread")
    public ResponseEntity<GetUnreadCountsUseCase.UnreadCounts> getUnreadCounts() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(getUnreadCountsUseCase.execute(email));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<GetDirectChatsUseCase.DirectChat>> getConversations() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(getDirectChatsUseCase.execute(email));
    }

    public static class ReadRequest {
        private String groupId;
        private String peerEmail;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getPeerEmail() {
            return peerEmail;
        }

        public void setPeerEmail(String peerEmail) {
            this.peerEmail = peerEmail;
        }
    }
}
