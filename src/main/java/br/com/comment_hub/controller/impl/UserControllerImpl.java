package br.com.comment_hub.controller.impl;

import br.com.comment_hub.controller.UserController;
import br.com.comment_hub.dto.request.UserRequest;
import br.com.comment_hub.enums.SortFields;
import br.com.comment_hub.utils.PaginationUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

    @Override
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserRequest userRequest) {
        // TODO: Implement user creation logic
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        // TODO: Implement user findById logic
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> findAll(@RequestParam @Min(0) int page,
                                                       @RequestParam @Min(0) @Max(10) int size,
                                                       @RequestParam(required = false) String sortBy) {
        // Demonstration of secure pagination implementation using PaginationUtils
        Pageable pageable = PaginationUtils.createPageableWithValidation(
                page, size, sortBy, SortFields.USER, SortFields.DEFAULT_USER_SORT);

        // TODO: Implement user findAll logic with userService.findAll(pageable)
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        // TODO: Implement user update logic
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        // TODO: Implement user delete logic
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
