package br.com.comment_hub.mapper;

import br.com.comment_hub.dto.response.AuthorResponse;
import br.com.comment_hub.model.core.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    private final UserMapper userMapper;

    public AuthorMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AuthorResponse toAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .user(author.getUser() != null ? userMapper.toUserResponse(author.getUser()) : null)
                .build();
    }
}