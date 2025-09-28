package br.com.comment_hub.controller.impl;

import br.com.comment_hub.controller.AuthController;
import br.com.comment_hub.dto.request.LoginRequest;
import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        Map<String, Object> message = authService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserRequest userRequest) {
        Map<String, Object> response = authService.register(userRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Map<String, Object> response = authService.logout();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
