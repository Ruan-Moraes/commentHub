package br.com.comment_hub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private AuthorResponse author;
    private Long postId;
    private String postTitle;
    private Date createdAt;
    private Date updatedAt;
}