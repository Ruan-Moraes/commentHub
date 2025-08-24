package br.com.comment_hub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @NotNull(message = "O conteúdo é obrigatório")
    @NotBlank(message = "O conteúdo não pode estar vazio")
    @Size(min = 1, max = 1000, message = "O conteúdo deve ter entre 1 e 1000 caracteres")
    private String content;

    @NotNull(message = "O ID do post é obrigatório")
    @Positive(message = "O ID do post deve ser um número positivo")
    private Long postId;
}