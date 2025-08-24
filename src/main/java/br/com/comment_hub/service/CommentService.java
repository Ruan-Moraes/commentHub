package br.com.comment_hub.service;

import br.com.comment_hub.dto.request.CommentRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CommentService {
    Map<String, Object> create(CommentRequest commentRequest, String authorEmail);
    
    Map<String, Object> findById(Long id);
    
    Map<String, Object> findAll(Pageable pageable);
    
    Map<String, Object> findByPostId(Long postId, Pageable pageable);
    
    Map<String, Object> update(Long id, CommentRequest commentRequest, String authorEmail);
    
    Map<String, Object> delete(Long id, String authorEmail);
}
