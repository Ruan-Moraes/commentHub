package br.com.comment_hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RegistrationConflictException extends RuntimeException {
    public RegistrationConflictException() {
        super("Ocorreu uma falha ao cadastra um novo usuário!");
    }
}
