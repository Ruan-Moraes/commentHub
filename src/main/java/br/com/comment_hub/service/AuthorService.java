package br.com.comment_hub.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AuthorService {
    Map<String, Object> findById(Long id);
    
    Map<String, Object> findAll(Pageable pageable);
    
    Map<String, Object> findPostsByAuthorId(Long authorId, Pageable pageable);
    
    Map<String, Object> findCommentsByAuthorId(Long authorId, Pageable pageable);
    
    Map<String, Object> getProfile(String userEmail);
}
