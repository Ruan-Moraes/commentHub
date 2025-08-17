package br.com.comment_hub.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super("Ocorreu uma falha ao cadastra um novo usuário!");
    }
}
