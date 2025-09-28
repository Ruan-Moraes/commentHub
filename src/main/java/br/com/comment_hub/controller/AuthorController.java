package br.com.comment_hub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "Autores", description = "Operações relacionadas ao gerenciamento de autores")
public interface AuthorController {
    @Operation(
            summary = "Buscar autor por ID",
            description = "Retorna as informações detalhadas de um autor específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autor encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "name": "João Silva",
                                                "email": "joao.silva@example.com",
                                                "createdAt": "2024-01-01T10:00:00Z",
                                                "postsCount": 5,
                                                "commentsCount": 23
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Autor não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Autor não encontrado",
                                                "message": "Não foi possível encontrar um autor com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> findById(
            @Parameter(description = "ID do autor", required = true, example = "1")
            @PathVariable Long id);

    @Operation(
            summary = "Listar todos os autores",
            description = "Retorna uma lista paginada de todos os autores cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de autores retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "name": "João Silva",
                                                        "email": "joao.silva@example.com",
                                                        "createdAt": "2024-01-01T10:00:00Z",
                                                        "postsCount": 5,
                                                        "commentsCount": 23
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 10,
                                                    "sort": {
                                                        "sorted": true,
                                                        "direction": "ASC",
                                                        "orderBy": ["name"]
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
                                                @Parameter(description = "Campo de ordenação (name, email, createdAt)", example = "name")
                                                @RequestParam(required = false) String sortBy);

    @Operation(
            summary = "Buscar posts de um autor",
            description = "Retorna uma lista paginada de todos os posts criados por um autor específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Posts do autor retornados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "title": "Meu primeiro post",
                                                        "content": "Este é o conteúdo do meu primeiro post...",
                                                        "createdAt": "2024-01-01T10:00:00Z",
                                                        "updatedAt": "2024-01-01T10:00:00Z",
                                                        "author": {
                                                            "id": 1,
                                                            "name": "João Silva",
                                                            "email": "joao.silva@example.com"
                                                        },
                                                        "commentsCount": 5
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 10
                                                },
                                                "totalElements": 1,
                                                "totalPages": 1
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Autor não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Autor não encontrado",
                                                "message": "Não foi possível encontrar um autor com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> findPostsByAuthorId(@Parameter(description = "ID do autor", required = true, example = "1")
                                                            @PathVariable Long id,
                                                            @Parameter(description = "Número da página (0-based)", example = "0")
                                                            @RequestParam @Min(0) int page,
                                                            @Parameter(description = "Tamanho da página (máximo 10)", example = "10")
                                                            @RequestParam @Min(0) @Max(10) int size,
                                                            @Parameter(description = "Campo de ordenação (title, createdAt, updatedAt)", example = "createdAt")
                                                            @RequestParam(required = false) String sortBy);

    @Operation(
            summary = "Buscar comentários de um autor",
            description = "Retorna uma lista paginada de todos os comentários criados por um autor específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentários do autor retornados com sucesso",
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
                                                            "title": "Título do post",
                                                            "content": "Conteúdo do post..."
                                                        }
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 10
                                                },
                                                "totalElements": 1,
                                                "totalPages": 1
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Autor não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Autor não encontrado",
                                                "message": "Não foi possível encontrar um autor com o ID fornecido"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> findCommentsByAuthorId(@Parameter(description = "ID do autor", required = true, example = "1")
                                                               @PathVariable Long id,
                                                               @Parameter(description = "Número da página (0-based)", example = "0")
                                                               @RequestParam int page,
                                                               @Parameter(description = "Tamanho da página (máximo 10)", example = "10")
                                                               @RequestParam @Min(0) @Max(10) int size,
                                                               @Parameter(description = "Campo de ordenação (content, createdAt, updatedAt)", example = "createdAt")
                                                               @RequestParam(required = false) String sortBy);

    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Obter perfil do autor autenticado",
            description = "Retorna as informações de perfil do autor atualmente autenticado"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil do autor retornado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "id": 1,
                                                "name": "João Silva",
                                                "email": "joao.silva@example.com",
                                                "createdAt": "2024-01-01T10:00:00Z",
                                                "postsCount": 5,
                                                "commentsCount": 23,
                                                "recentPosts": [
                                                    {
                                                        "id": 1,
                                                        "title": "Meu post mais recente",
                                                        "createdAt": "2024-01-10T15:30:00Z"
                                                    }
                                                ],
                                                "recentComments": [
                                                    {
                                                        "id": 1,
                                                        "content": "Comentário recente...",
                                                        "createdAt": "2024-01-10T16:00:00Z"
                                                    }
                                                ]
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
                                                "error": "Não autenticado",
                                                "message": "Token JWT necessário para acessar este recurso"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> getProfile();
}
