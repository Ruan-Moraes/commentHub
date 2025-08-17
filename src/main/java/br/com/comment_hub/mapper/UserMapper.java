package br.com.comment_hub.mapper;

import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.dto.response.UserResponse;
import br.com.comment_hub.model.core.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
