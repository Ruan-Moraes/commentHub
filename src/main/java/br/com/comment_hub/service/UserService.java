package br.com.comment_hub.service;

import br.com.comment_hub.dto.request.PasswordChangeRequest;
import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse create(UserRequest userRequest);

    UserResponse findById(Long id);

    Page<UserResponse> findAll(Pageable pageable);

    UserResponse update(Long id, UserRequest userRequest);

    void delete(Long id);

    UserResponse activate(Long id);

    UserResponse deactivate(Long id);

    void changePassword(Long id, PasswordChangeRequest passwordChangeRequest);
}
