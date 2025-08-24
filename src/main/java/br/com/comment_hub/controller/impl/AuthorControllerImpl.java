package br.com.comment_hub.controller.impl;

import br.com.comment_hub.controller.AuthorController;
import br.com.comment_hub.service.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authors")
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Map<String, Object> response = authorService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping(value = "/")
    public ResponseEntity<Map<String, Object>> findAll(Pageable pageable) {
        Map<String, Object> response = authorService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/{id}/posts")
    public ResponseEntity<Map<String, Object>> findPostsByAuthorId(@PathVariable Long id, Pageable pageable) {
        Map<String, Object> response = authorService.findPostsByAuthorId(id, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/{id}/comments")
    public ResponseEntity<Map<String, Object>> findCommentsByAuthorId(@PathVariable Long id, Pageable pageable) {
        Map<String, Object> response = authorService.findCommentsByAuthorId(id, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();
        
        Map<String, Object> response = authorService.getProfile(userEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}