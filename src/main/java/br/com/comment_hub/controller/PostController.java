package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.PostRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface PostController {
    ResponseEntity<Map<String, Object>> create(@Valid @RequestBody PostRequest postRequest);
    
    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id);
    
    ResponseEntity<Map<String, Object>> findAll(Pageable pageable);
    
    ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest);
    
    ResponseEntity<Map<String, Object>> delete(@PathVariable Long id);
}
