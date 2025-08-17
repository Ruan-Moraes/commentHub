package br.com.comment_hub.service.impl;

import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.exception.RegistrationException;
import br.com.comment_hub.mapper.UserMapper;
import br.com.comment_hub.model.User;
import br.com.comment_hub.repository.UserRepository;
import br.com.comment_hub.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final TokenServiceImpl tokenService;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenServiceImpl tokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Map<String, Object> login(UserRequest userRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();
        user.setName(userRepository.selectNameByEmail(user.getEmail()));

        String token = tokenService.generateToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuário logado com sucesso!");
        response.put("user", userMapper.toUserResponse(user));
        response.put("token", token);

        return response;
    }

    @Override
    public Map<String, Object> register(UserRequest userRequest) throws RegistrationException {
        String password = passwordEncoder.encode(userRequest.getPassword());

        userRequest.setPassword(password);

        User user = userRepository.save(userMapper.toUser(userRequest));

        String token = tokenService.generateToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuário cadastrado com sucesso!");
        response.put("user", userMapper.toUserResponse(user));
        response.put("token", token);

        return response;
    }

    @Override
    public Map<String, Object> logout() {
        return new HashMap<>();
    }
}
