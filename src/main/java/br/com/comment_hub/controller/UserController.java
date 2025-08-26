package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface UserController {
    ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserRequest userRequest);

    ResponseEntity<Map<String, Object>> findById(@PathVariable Long id);

    ResponseEntity<Map<String, Object>> findAll(@RequestParam @Min(0) int page,
                                                @RequestParam @Min(0) @Max(10) int size,
                                                @RequestParam(required = false) String sortBy);

    ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest);

    ResponseEntity<Map<String, Object>> delete(@PathVariable Long id);
}
