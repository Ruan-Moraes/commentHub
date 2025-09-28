package br.com.comment_hub.service.impl;

import br.com.comment_hub.dto.request.PasswordChangeRequest;
import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.dto.response.UserResponse;
import br.com.comment_hub.exception.NotFoundException;
import br.com.comment_hub.exception.ValidationException;
import br.com.comment_hub.mapper.UserMapper;
import br.com.comment_hub.model.core.User;
import br.com.comment_hub.repository.UserRepository;
import br.com.comment_hub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        log.info("Creating new user with email: {}", userRequest.getEmail());

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ValidationException("Email já está em uso");
        }

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("Finding user by ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        log.info("Finding all users with pagination: page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!user.getEmail().equals(userRequest.getEmail()) &&
                userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ValidationException("Email já está em uso");
        }

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());

        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.setActive(false);
        userRepository.save(user);

        log.info("User soft deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public UserResponse activate(Long id) {
        log.info("Activating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.setActive(true);
        User activatedUser = userRepository.save(user);

        log.info("User activated successfully with ID: {}", id);
        return userMapper.toResponse(activatedUser);
    }

    @Override
    @Transactional
    public UserResponse deactivate(Long id) {
        log.info("Deactivating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.setActive(false);
        User deactivatedUser = userRepository.save(user);

        log.info("User deactivated successfully with ID: {}", id);
        return userMapper.toResponse(deactivatedUser);
    }

    @Override
    @Transactional
    public void changePassword(Long id, PasswordChangeRequest passwordChangeRequest) {
        log.info("Changing password for user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())) {
            throw new ValidationException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);

        log.info("Password changed successfully for user with ID: {}", id);
    }
}
