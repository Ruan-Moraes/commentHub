package br.com.comment_hub.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface AuthorController {
    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id);
    
    ResponseEntity<Map<String, Object>> findAll(Pageable pageable);
    
    ResponseEntity<Map<String, Object>> findPostsByAuthorId(@PathVariable Long id, Pageable pageable);
    
    ResponseEntity<Map<String, Object>> findCommentsByAuthorId(@PathVariable Long id, Pageable pageable);
    
    ResponseEntity<Map<String, Object>> getProfile();
}
