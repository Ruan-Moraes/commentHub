package br.com.comment_hub.controller;

import br.com.comment_hub.dto.request.LoginRequest;
import br.com.comment_hub.dto.request.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Autenticação", description = "Operações relacionadas à autenticação de usuários")
public interface AuthController {
    @Operation(
            summary = "Realizar login",
            description = "Autentica um usuário com email e senha, retornando um token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                        "message": "Login realizado com sucesso",
                                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                        "user": {
                                            "id": 1,
                                            "email": "user@example.com",
                                            "name": "João Silva"
                                        }
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Credenciais inválidas",
                                                "message": "Email ou senha incorretos"
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
                                                "details": ["O email deve ter um formato válido"]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> login(
            @Parameter(description = "Dados de login do usuário", required = true)
            LoginRequest loginRequest);

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário registrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Usuário registrado com sucesso",
                                                "user": {
                                                    "id": 1,
                                                    "email": "user@example.com",
                                                    "name": "João Silva"
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito - Email já cadastrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Email já cadastrado",
                                                "message": "Este email já está em uso por outro usuário"
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
                                                "details": ["O nome é obrigatório", "O email deve ter um formato válido"]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> register(
            @Parameter(description = "Dados do novo usuário", required = true)
            UserRequest userRequest);

    @Operation(
            summary = "Realizar logout",
            description = "Invalida o token JWT do usuário autenticado"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Logout realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "message": "Logout realizado com sucesso"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou expirado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "error": "Token inválido",
                                                "message": "Token JWT inválido ou expirado"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Map<String, Object>> logout();
}
