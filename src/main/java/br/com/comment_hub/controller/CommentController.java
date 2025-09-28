package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.CommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "Comentários", description = "Operações relacionadas ao gerenciamento de comentários")
public interface CommentController {
    @Operation(
            summary = "Criar comentário",
            description = "Cria um novo comentário associado a um post específico"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comentário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Comentário criado com sucesso",
                                                "comment": {
                                                    "id": 1,
                                                    "content": "Este é um comentário muito interessante!",
                                                    "createdAt": "2024-01-01T10:30:00Z",
                                                    "updatedAt": "2024-01-01T10:30:00Z",
                                                    "author": {
                                                        "id": 1,
                                                        "name": "João Silva",
                                                        "email": "joao.silva@example.com"
                                                    },
                                                    "post": {
                                                        "id": 1,
                                                        "title": "Título do post"
                                                    }
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Dados inválidos",
                                                "details": ["O conteúdo é obrigatório", "O ID do post deve ser um número positivo"]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Não autorizado",
                                                "message": "Token JWT necessário para criar comentários"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Post não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Post não encontrado",
                                                "message": "Não foi possível encontrar um post com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> create(
            @Parameter(description = "Dados do comentário", required = true)
            @Valid @RequestBody CommentRequest commentRequest);

    @Operation(
            summary = "Buscar comentário por ID",
            description = "Retorna um comentário específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentário encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "content": "Este é um comentário muito interessante!",
                                                "createdAt": "2024-01-01T10:30:00Z",
                                                "updatedAt": "2024-01-01T10:30:00Z",
                                                "author": {
                                                    "id": 1,
                                                    "name": "João Silva",
                                                    "email": "joao.silva@example.com"
                                                },
                                                "post": {
                                                    "id": 1,
                                                    "title": "Título do post",
                                                    "content": "Conteúdo do post..."
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Comentário não encontrado",
                                                "message": "Não foi possível encontrar um comentário com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> findById(@Parameter(description = "ID do comentário", required = true, example = "1")
                                                 @PathVariable Long id);

    @Operation(
            summary = "Listar todos os comentários",
            description = "Retorna uma lista paginada de todos os comentários do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de comentários retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "content": "Este é um comentário muito interessante!",
                                                        "createdAt": "2024-01-01T10:30:00Z",
                                                        "updatedAt": "2024-01-01T10:30:00Z",
                                                        "author": {
                                                            "id": 1,
                                                            "name": "João Silva",
                                                            "email": "joao.silva@example.com"
                                                        },
                                                        "post": {
                                                            "id": 1,
                                                            "title": "Título do post"
                                                        }
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 10,
                                                    "sort": {
                                                        "sorted": true,
                                                        "direction": "DESC",
                                                        "orderBy": ["createdAt"]
                                                    }
                                                },
                                                "totalElements": 1,
                                                "totalPages": 1,
                                                "last": true,
                                                "first": true,
                                                "numberOfElements": 1
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> findAll(@Parameter(description = "Número da página (0-based)", example = "0")
                                                @RequestParam @Min(0) int page,
                                                @Parameter(description = "Tamanho da página (máximo 10)", example = "10")
                                                @RequestParam @Min(0) @Max(10) int size,
                                                @Parameter(description = "Campo de ordenação (content, createdAt, updatedAt)", example = "createdAt")
                                                @RequestParam(required = false) String sortBy);

    @Operation(
            summary = "Buscar comentários por ID do post",
            description = "Retorna uma lista paginada de comentários associados a um post específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentários do post retornados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "content": "Este é um comentário muito interessante!",
                                                        "createdAt": "2024-01-01T10:30:00Z",
                                                        "updatedAt": "2024-01-01T10:30:00Z",
                                                        "author": {
                                                            "id": 1,
                                                            "name": "João Silva",
                                                            "email": "joao.silva@example.com"
                                                        }
                                                    },
                                                    {
                                                        "id": 2,
                                                        "content": "Concordo totalmente!",
                                                        "createdAt": "2024-01-01T11:00:00Z",
                                                        "updatedAt": "2024-01-01T11:00:00Z",
                                                        "author": {
                                                            "id": 2,
                                                            "name": "Maria Santos",
                                                            "email": "maria.santos@example.com"
                                                        }
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 10,
                                                    "sort": {
                                                        "sorted": true,
                                                        "direction": "ASC",
                                                        "orderBy": ["createdAt"]
                                                    }
                                                },
                                                "totalElements": 2,
                                                "totalPages": 1,
                                                "last": true,
                                                "first": true,
                                                "numberOfElements": 2
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Post não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Post não encontrado",
                                                "message": "Não foi possível encontrar um post com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> findByPostId(@Parameter(description = "ID do post", required = true, example = "1")
                                                     @PathVariable Long postId,
                                                     @Parameter(description = "Número da página (0-based)", example = "0")
                                                     @RequestParam @Min(0) int page,
                                                     @Parameter(description = "Tamanho da página (máximo 10)", example = "10")
                                                     @RequestParam @Min(0) @Max(10) int size,
                                                     @Parameter(description = "Campo de ordenação (content, createdAt, updatedAt)", example = "createdAt")
                                                     @RequestParam(required = false) String sortBy);

    @Operation(
            summary = "Atualizar comentário",
            description = "Atualiza um comentário existente. Apenas o autor do comentário pode atualizá-lo"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Comentário atualizado com sucesso",
                                                "comment": {
                                                    "id": 1,
                                                    "content": "Comentário atualizado com novo conteúdo!",
                                                    "createdAt": "2024-01-01T10:30:00Z",
                                                    "updatedAt": "2024-01-01T14:30:00Z",
                                                    "author": {
                                                        "id": 1,
                                                        "name": "João Silva",
                                                        "email": "joao.silva@example.com"
                                                    },
                                                    "post": {
                                                        "id": 1,
                                                        "title": "Título do post"
                                                    }
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Dados inválidos",
                                                "details": ["O conteúdo é obrigatório", "O conteúdo deve ter entre 1 e 1000 caracteres"]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Não autorizado",
                                                "message": "Token JWT necessário para atualizar comentários"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é o autor do comentário",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Acesso negado",
                                                "message": "Apenas o autor pode atualizar este comentário"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Comentário não encontrado",
                                                "message": "Não foi possível encontrar um comentário com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> update(
            @Parameter(description = "ID do comentário", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do comentário", required = true)
            @Valid @RequestBody CommentRequest commentRequest
    );

    @Operation(
            summary = "Deletar comentário",
            description = "Remove um comentário do sistema. Apenas o autor do comentário pode deletá-lo"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentário deletado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Comentário deletado com sucesso"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Não autorizado",
                                                "message": "Token JWT necessário para deletar comentários"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é o autor do comentário",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Acesso negado",
                                                "message": "Apenas o autor pode deletar este comentário"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comentário não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Comentário não encontrado",
                                                "message": "Não foi possível encontrar um comentário com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> delete(@Parameter(description = "ID do comentário", required = true, example = "1")
                                               @PathVariable Long id);
}
