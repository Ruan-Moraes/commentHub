package br.com.comment_hub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder()
@Data()
@AllArgsConstructor()
@NoArgsConstructor()
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private Boolean active;

    private LocalDateTime createdAt;
}
