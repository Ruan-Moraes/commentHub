package br.com.comment_hub.service.impl;

import br.com.comment_hub.dto.request.LoginRequest;
import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.exception.LoginException;
import br.com.comment_hub.exception.RegistrationConflictException;
import br.com.comment_hub.mapper.UserMapper;
import br.com.comment_hub.model.core.User;
import br.com.comment_hub.repository.UserRepository;
import br.com.comment_hub.service.AuthService;
import br.com.comment_hub.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Map<String, Object> login(LoginRequest loginRequest) throws LoginException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new LoginException();
        }

        User user = (User) authentication.getPrincipal();
        user.setName(userRepository.selectNameByEmail(user.getEmail()));

        String token = tokenService.generateToken(user);

        Map<String, Object> response = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("user", userMapper.toUserResponse(user));
        data.put("token", token);

        response.put("data", data);
        response.put("message", "Usuário logado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> register(UserRequest userRequest) throws RegistrationConflictException {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RegistrationConflictException();
        }

        String password = passwordEncoder.encode(userRequest.getPassword());

        userRequest.setPassword(password);

        User user = userRepository.save(userMapper.toUser(userRequest));

        String token = tokenService.generateToken(user);

        Map<String, Object> response = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("user", userMapper.toUserResponse(user));
        data.put("token", token);

        response.put("data", data);
        response.put("message", "Usuário cadastrado com sucesso!");

        return response;
    }

    @Override
    public Map<String, Object> logout() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logout realizado com sucesso!");

        return response;
    }
}
