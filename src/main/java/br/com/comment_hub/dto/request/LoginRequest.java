package br.com.comment_hub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para login do usuário")
public class LoginRequest {
    @NotNull(message = "O email é obrigatório")
    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "O email deve ter um formato válido")
    @Schema(description = "Email do usuário", example = "usuario@example.com", required = true)
    private String email;

    @NotNull(message = "A senha é obrigatória")
    @NotBlank(message = "A senha não pode estar vazia")
    @Size(min = 6, message = "O campo da senha deve ter no mínimo 6 caracteres")
    @Schema(description = "Senha do usuário", example = "minhasenha123", required = true, minLength = 6)
    private String password;
}