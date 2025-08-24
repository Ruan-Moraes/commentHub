package br.com.comment_hub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotNull(message = "O título é obrigatório")
    @NotBlank(message = "O título não pode estar vazio")
    @Size(min = 5, max = 200, message = "O título deve ter entre 5 e 200 caracteres")
    private String title;

    @NotNull(message = "O conteúdo é obrigatório")
    @NotBlank(message = "O conteúdo não pode estar vazio")
    private String content;
}