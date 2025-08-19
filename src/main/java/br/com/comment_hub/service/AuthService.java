package br.com.comment_hub.service;

import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.exception.LoginException;
import br.com.comment_hub.exception.RegistrationConflictException;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(UserRequest userRequest) throws LoginException;
    Map<String, Object> register(UserRequest userRequest) throws RegistrationConflictException;
    Map<String, Object> logout();
}
