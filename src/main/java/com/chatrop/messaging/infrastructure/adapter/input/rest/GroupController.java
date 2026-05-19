package com.chatrop.messaging.infrastructure.adapter.input.rest;

import com.chatrop.messaging.application.usecase.AddMemberToGroupUseCase;
import com.chatrop.messaging.application.usecase.CreateGroupUseCase;
import com.chatrop.messaging.application.usecase.GetGroupsForUserUseCase;
import com.chatrop.messaging.domain.model.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;
    private final AddMemberToGroupUseCase addMemberToGroupUseCase;
    private final GetGroupsForUserUseCase getGroupsForUserUseCase;

    public GroupController(CreateGroupUseCase createGroupUseCase, 
                           AddMemberToGroupUseCase addMemberToGroupUseCase, 
                           GetGroupsForUserUseCase getGroupsForUserUseCase) {
        this.createGroupUseCase = createGroupUseCase;
        this.addMemberToGroupUseCase = addMemberToGroupUseCase;
        this.getGroupsForUserUseCase = getGroupsForUserUseCase;
    }

    // Obtener todos los grupos del usuario autenticado
    @GetMapping
    public ResponseEntity<List<Group>> getMyGroups(Authentication authentication) {
        String email = authentication.getName(); // Extraemos el email seguro desde el JWT
        return ResponseEntity.ok(getGroupsForUserUseCase.execute(email));
    }

    // Crear un grupo nuevo
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Map<String, String> request, Authentication authentication) {
        String groupName = request.get("name");
        String creatorEmail = authentication.getName();
        
        if (groupName == null || groupName.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(createGroupUseCase.execute(groupName, creatorEmail));
    }

    // Añadir un miembro a un grupo existente
    @PostMapping("/{groupId}/members")
    public ResponseEntity<Group> addMember(@PathVariable String groupId, @RequestBody Map<String, String> request) {
        String userEmail = request.get("email");
        
        if (userEmail == null || userEmail.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(addMemberToGroupUseCase.execute(groupId, userEmail));
    }
}
