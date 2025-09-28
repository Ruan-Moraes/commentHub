package br.com.comment_hub.controller.impl;

import br.com.comment_hub.controller.PostController;
import br.com.comment_hub.dto.request.PostRequest;
import br.com.comment_hub.enums.SortFields;
import br.com.comment_hub.service.PostService;
import br.com.comment_hub.utils.PaginationUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostControllerImpl implements PostController {
    private final PostService postService;

    public PostControllerImpl(PostService postService) {
        this.postService = postService;
    }

    @Override
    @PostMapping("/")
    @PreAuthorize("hasAuthority('post:write')")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody PostRequest postRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorEmail = authentication.getName();

        Map<String, Object> response = postService.create(postRequest, authorEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Map<String, Object> response = postService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam @Min(0) int page,
            @RequestParam @Min(0) @Max(10) int size,
            @RequestParam(required = false) String sortBy
    ) {
        Pageable pageable = PaginationUtils.createPageableWithValidation(
                page, size, sortBy, SortFields.POST, SortFields.DEFAULT_POST_SORT);

        Map<String, Object> response = postService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('post:write')")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest postRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorEmail = authentication.getName();

        Map<String, Object> response = postService.update(id, postRequest, authorEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('post:write')")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorEmail = authentication.getName();

        Map<String, Object> response = postService.delete(id, authorEmail);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}