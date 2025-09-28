package br.com.comment_hub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para criação ou atualização de um comentário")
public class CommentRequest {
    @NotNull(message = "O conteúdo é obrigatório")
    @NotBlank(message = "O conteúdo não pode estar vazio")
    @Size(min = 1, max = 1000, message = "O conteúdo deve ter entre 1 e 1000 caracteres")
    @Schema(description = "Conteúdo do comentário", example = "Este é um comentário muito interessante sobre o post!", required = true, minLength = 1, maxLength = 1000)
    private String content;

    @NotNull(message = "O ID do post é obrigatório")
    @Positive(message = "O ID do post deve ser um número positivo")
    @Schema(description = "ID do post ao qual o comentário será associado", example = "1", required = true, minimum = "1")
    private Long postId;
}