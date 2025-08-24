package br.com.comment_hub.mapper;

import br.com.comment_hub.dto.request.CommentRequest;
import br.com.comment_hub.dto.response.CommentResponse;
import br.com.comment_hub.model.core.Comment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {
    private final AuthorMapper authorMapper;

    public CommentMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public Comment toComment(CommentRequest commentRequest) {
        return Comment.builder()
                .content(commentRequest.getContent())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    public CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor() != null ? authorMapper.toAuthorResponse(comment.getAuthor()) : null)
                .postId(comment.getPost() != null ? comment.getPost().getId() : null)
                .postTitle(comment.getPost() != null ? comment.getPost().getTitle() : null)
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public void updateCommentFromRequest(Comment comment, CommentRequest commentRequest) {
        comment.setContent(commentRequest.getContent());
        comment.setUpdatedAt(new Date());
    }
}