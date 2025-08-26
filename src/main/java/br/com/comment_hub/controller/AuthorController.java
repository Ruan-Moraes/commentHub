package br.com.comment_hub.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface AuthorController {
    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id);

    ResponseEntity<Map<String, Object>> findAll(@RequestParam @Min(0) int page,
                                                @RequestParam @Min(0) @Max(10) int size,
                                                @RequestParam(required = false) String sortBy);

    ResponseEntity<Map<String, Object>> findPostsByAuthorId(@PathVariable Long id,
                                                            @RequestParam @Min(0) int page,
                                                            @RequestParam @Min(0) @Max(10) int size,
                                                            @RequestParam(required = false) String sortBy);

    ResponseEntity<Map<String, Object>> findCommentsByAuthorId(@PathVariable Long id,
                                                               @RequestParam @Min(0) int page,
                                                               @RequestParam @Min(0) @Max(10) int size,
                                                               @RequestParam(required = false) String sortBy);

    ResponseEntity<Map<String, Object>> getProfile();
}
