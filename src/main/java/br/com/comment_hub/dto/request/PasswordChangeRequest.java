package br.com.comment_hub.dto.request;

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
public class PasswordChangeRequest {
    @NotNull(message = "A senha atual é obrigatória")
    @NotBlank(message = "A senha atual não pode estar vazia")
    private String oldPassword;

    @NotNull(message = "A nova senha é obrigatória")
    @NotBlank(message = "A nova senha não pode estar vazia")
    @Size(min = 6, message = "A nova senha deve ter no mínimo 6 caracteres")
    private String newPassword;
}