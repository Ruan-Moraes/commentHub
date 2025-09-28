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
public class RegisterRequest {
    @NotNull(message = "O nome é obrigatório")
    @NotBlank(message = "O nome não pode estar vazio")
    @Size(min = 2, message = "O campo nome deve ter no mínimo 2 caracteres")
    private String name;

    @NotNull()
    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "O email deve ter um formato válido")
    private String email;

    @NotNull(message = "A senha é obrigatória")
    @NotBlank(message = "A senha não pode estar vazia")
    @Size(min = 6, message = "O campo da senha deve ter no mínimo 6 caracteres")
    private String password;
}