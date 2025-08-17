package br.com.comment_hub.service;

import br.com.comment_hub.dto.request.UserRequest;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(UserRequest userRequest);
    Map<String, Object> register(UserRequest userRequest);
    Map<String, Object> logout();
}
