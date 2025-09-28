package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários")
public interface UserController {
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário no sistema")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    ResponseEntity<Map<String, Object>> create(@Parameter(description = "Dados do usuário", required = true)
                                               @Valid @RequestBody UserRequest userRequest);

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Map<String, Object>> findById(@Parameter(description = "ID do usuário", required = true)
                                                 @PathVariable Long id);

    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de usuários")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    ResponseEntity<Map<String, Object>> findAll(@Parameter(description = "Número da página (0-based)")
                                                @RequestParam @Min(0) int page,
                                                @Parameter(description = "Tamanho da página (máximo 10)")
                                                @RequestParam @Min(0) @Max(10) int size,
                                                @Parameter(description = "Campo de ordenação")
                                                @RequestParam(required = false) String sortBy);

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<Map<String, Object>> update(@Parameter(description = "ID do usuário", required = true)
                                               @PathVariable Long id,
                                               @Parameter(description = "Novos dados do usuário", required = true)
                                               @Valid @RequestBody UserRequest userRequest);

    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Map<String, Object>> delete(@Parameter(description = "ID do usuário", required = true)
                                               @PathVariable Long id);
}
