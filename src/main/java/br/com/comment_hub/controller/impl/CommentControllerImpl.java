package br.com.comment_hub.controller.impl;

import br.com.comment_hub.controller.CommentController;
import br.com.comment_hub.dto.request.CommentRequest;
import br.com.comment_hub.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentControllerImpl implements CommentController {
    private final CommentService commentService;

    public CommentControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @PostMapping("/")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorEmail = authentication.getName();
        
        Map<String, Object> response = commentService.create(commentRequest, authorEmail);


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Map<String, Object> response = commentService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> findAll(Pageable pageable) {
        Map<String, Object> response = commentService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/post/{postId}")
    public ResponseEntity<Map<String, Object>> findByPostId(@PathVariable Long postId, Pageable pageable) {
        Map<String, Object> response = commentService.findByPostId(postId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorEmail = authentication.getName();
        
        Map<String, Object> response = commentService.update(id, commentRequest, authorEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorEmail = authentication.getName();
        
        Map<String, Object> response = commentService.delete(id, authorEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}