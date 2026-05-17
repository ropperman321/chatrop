package com.chatrop.users.infrastructure.adapter.input.rest;

import com.chatrop.users.application.usecase.LoginUserUseCase;
import com.chatrop.users.application.usecase.RegisterUserUseCase;
import com.chatrop.users.domain.model.User;
import com.chatrop.users.infrastructure.adapter.input.rest.dto.LoginRequest;
import com.chatrop.users.infrastructure.adapter.input.rest.dto.LoginResponse;
import com.chatrop.users.infrastructure.adapter.input.rest.dto.RegisterUserRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    // Constructor manual: Inyecta el caso de uso sin depender de Lombok
    public UserController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
        User user = registerUserUseCase.execute(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("Usuario registrado con éxito: " + user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("Intentando login para: " + request.getEmail()); // LOG DE PRUEBA

            // Ahora execute devuelve directamente el String del token
            String token = loginUserUseCase.execute(request.getEmail(), request.getPassword());

            // Devolvemos el token en el cuerpo de la respuesta
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (RuntimeException e) {
            System.err.println("Error en login: " + e.getMessage()); // ESTO SALDRÁ EN ROJO EN TU LOG

            // Si las credenciales fallan, capturamos la excepción del caso de uso
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMe() {
        // El filtro de seguridad guardó aquí la información del token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No estás autenticado");
        }

        String email = auth.getName(); // Aquí recuperamos el email que metimos en el filtro
        return ResponseEntity.ok("Estás logueado como: " + email);
    }
}
