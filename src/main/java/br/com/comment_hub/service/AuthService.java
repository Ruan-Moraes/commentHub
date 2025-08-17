package br.com.comment_hub.service;

import br.com.comment_hub.dto.request.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    public Map<String, Object> login(UserRequest userRequest);
    public Map<String, Object> register(UserRequest userRequest);
    public Map<String, Object> logout();
}
