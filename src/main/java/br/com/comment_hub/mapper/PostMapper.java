package br.com.comment_hub.mapper;

import br.com.comment_hub.dto.request.PostRequest;
import br.com.comment_hub.dto.response.PostResponse;
import br.com.comment_hub.model.core.Post;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostMapper {
    private final AuthorMapper authorMapper;

    public PostMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public Post toPost(PostRequest postRequest) {
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor() != null ? authorMapper.toAuthorResponse(post.getAuthor()) : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public void updatePostFromRequest(Post post, PostRequest postRequest) {
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUpdatedAt(new Date());
    }
}