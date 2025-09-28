package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.PostRequest;
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

@Tag(name = "Posts", description = "Operações relacionadas ao gerenciamento de posts")
public interface PostController {
    @Operation(
            summary = "Criar post",
            description = "Cria um novo post no sistema"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Post criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Post criado com sucesso",
                                                "post": {
                                                    "id": 1,
                                                    "title": "Meu primeiro post",
                                                    "content": "Este é o conteúdo do meu primeiro post sobre tecnologia...",
                                                    "createdAt": "2024-01-01T10:00:00Z",
                                                    "updatedAt": "2024-01-01T10:00:00Z",
                                                    "author": {
                                                        "id": 1,
                                                        "name": "João Silva",
                                                        "email": "joao.silva@example.com"
                                                    },
                                                    "commentsCount": 0
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
                                                "details": ["O título é obrigatório", "O título deve ter entre 5 e 200 caracteres", "O conteúdo é obrigatório"]
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
                                                "message": "Token JWT necessário para criar posts"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Permissão insuficiente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Acesso negado",
                                                "message": "Usuário não possui permissão para criar posts"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> create(@Parameter(description = "Dados do post", required = true)
                                               @Valid @RequestBody PostRequest postRequest);

    @Operation(
            summary = "Buscar post por ID",
            description = "Retorna um post específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Post encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "title": "Meu primeiro post",
                                                "content": "Este é o conteúdo do meu primeiro post sobre tecnologia...",
                                                "createdAt": "2024-01-01T10:00:00Z",
                                                "updatedAt": "2024-01-01T10:00:00Z",
                                                "author": {
                                                    "id": 1,
                                                    "name": "João Silva",
                                                    "email": "joao.silva@example.com"
                                                },
                                                "comments": [
                                                    {
                                                        "id": 1,
                                                        "content": "Muito interessante!",
                                                        "createdAt": "2024-01-01T11:00:00Z",
                                                        "author": {
                                                            "id": 2,
                                                            "name": "Maria Santos"
                                                        }
                                                    }
                                                ],
                                                "commentsCount": 1
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
    ResponseEntity<Map<String, Object>> findById(@Parameter(description = "ID do post", required = true, example = "1")
                                                 @PathVariable Long id);

    @Operation(
            summary = "Listar todos os posts",
            description = "Retorna uma lista paginada de todos os posts do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de posts retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "title": "Meu primeiro post",
                                                        "content": "Este é o conteúdo do meu primeiro post sobre tecnologia...",
                                                        "createdAt": "2024-01-01T10:00:00Z",
                                                        "updatedAt": "2024-01-01T10:00:00Z",
                                                        "author": {
                                                            "id": 1,
                                                            "name": "João Silva",
                                                            "email": "joao.silva@example.com"
                                                        },
                                                        "commentsCount": 5
                                                    },
                                                    {
                                                        "id": 2,
                                                        "title": "Segundo post interessante",
                                                        "content": "Outro conteúdo muito interessante...",
                                                        "createdAt": "2024-01-02T14:30:00Z",
                                                        "updatedAt": "2024-01-02T14:30:00Z",
                                                        "author": {
                                                            "id": 2,
                                                            "name": "Maria Santos",
                                                            "email": "maria.santos@example.com"
                                                        },
                                                        "commentsCount": 3
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
                                                "totalElements": 2,
                                                "totalPages": 1,
                                                "last": true,
                                                "first": true,
                                                "numberOfElements": 2
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
                                                @Parameter(description = "Campo de ordenação (title, createdAt, updatedAt)", example = "createdAt")
                                                @RequestParam(required = false) String sortBy);

    @Operation(
            summary = "Atualizar post",
            description = "Atualiza um post existente. Apenas o autor do post pode atualizá-lo"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Post atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Post atualizado com sucesso",
                                                "post": {
                                                    "id": 1,
                                                    "title": "Título do post atualizado",
                                                    "content": "Conteúdo do post foi atualizado com novas informações...",
                                                    "createdAt": "2024-01-01T10:00:00Z",
                                                    "updatedAt": "2024-01-01T15:30:00Z",
                                                    "author": {
                                                        "id": 1,
                                                        "name": "João Silva",
                                                        "email": "joao.silva@example.com"
                                                    },
                                                    "commentsCount": 5
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
                                                "details": ["O título é obrigatório", "O título deve ter entre 5 e 200 caracteres"]
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
                                                "message": "Token JWT necessário para atualizar posts"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é o autor do post",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Acesso negado",
                                                "message": "Apenas o autor pode atualizar este post"
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
    ResponseEntity<Map<String, Object>> update(@Parameter(description = "ID do post", required = true, example = "1")
                                               @PathVariable Long id,
                                               @Parameter(description = "Novos dados do post", required = true)
                                               @Valid @RequestBody PostRequest postRequest);

    @Operation(
            summary = "Deletar post",
            description = "Remove um post do sistema. Apenas o autor do post pode deletá-lo"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Post deletado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Post deletado com sucesso"
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
                                                "message": "Token JWT necessário para deletar posts"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - usuário não é o autor do post",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Acesso negado",
                                                "message": "Apenas o autor pode deletar este post"
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
    ResponseEntity<Map<String, Object>> delete(
            @Parameter(description = "ID do post", required = true, example = "1")
            @PathVariable Long id
    );
}
