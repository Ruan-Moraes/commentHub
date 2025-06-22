package br.com.comment_hub.exception;

public class TokenExpection extends RuntimeException {
    public TokenExpection() {
        super("Token inválido ou expirado. Por favor, faça login novamente.");
    }
}
