package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthController {
    public ResponseEntity<Map<String, Object>> login(UserRequest userRequest);
    public ResponseEntity<Map<String, Object>> register(UserRequest userRequest);
    public ResponseEntity<Map<String, Object>> logout();
}
