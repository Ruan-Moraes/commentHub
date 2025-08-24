package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.CommentRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface CommentController {
    ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CommentRequest commentRequest);
    
    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id);
    
    ResponseEntity<Map<String, Object>> findAll(Pageable pageable);
    
    ResponseEntity<Map<String, Object>> findByPostId(@PathVariable Long postId, Pageable pageable);
    
    ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest);
    
    ResponseEntity<Map<String, Object>> delete(@PathVariable Long id);
}
