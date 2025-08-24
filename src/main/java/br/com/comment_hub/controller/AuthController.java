package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.LoginRequest;
import br.com.comment_hub.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthController {
    ResponseEntity<Map<String, Object>> login(LoginRequest loginRequest);

    ResponseEntity<Map<String, Object>> register(UserRequest userRequest);

    ResponseEntity<Map<String, Object>> logout();
}
