package br.com.comment_hub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para criação ou atualização de um post")
public class PostRequest {
    @NotNull(message = "O título é obrigatório")
    @NotBlank(message = "O título não pode estar vazio")
    @Size(min = 5, max = 200, message = "O título deve ter entre 5 e 200 caracteres")
    @Schema(description = "Título do post", example = "Meu primeiro post sobre tecnologia", required = true, minLength = 5, maxLength = 200)
    private String title;

    @NotNull(message = "O conteúdo é obrigatório")
    @NotBlank(message = "O conteúdo não pode estar vazio")
    @Schema(description = "Conteúdo completo do post", example = "Este é o conteúdo detalhado do meu post sobre as últimas tendências em tecnologia...", required = true)
    private String content;
}