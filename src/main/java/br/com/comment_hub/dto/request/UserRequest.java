package br.com.comment_hub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder()
@Data()
@AllArgsConstructor()
@NoArgsConstructor()
public class UserRequest {
    private String name;

    @NotNull()
    @NotBlank()
    @Email()
    private String email;

    @NotNull()
    @NotBlank()
    @Size(min = 6, message = "O campo da senha deve ter no mínimo 6 caracteres")
    private String password;
}
