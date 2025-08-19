package br.com.comment_hub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder()
@Data()
@AllArgsConstructor()
@NoArgsConstructor()
public class UserResponse {
    private String name;

    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
}
