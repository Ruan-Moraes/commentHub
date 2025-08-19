package br.com.comment_hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginException extends RuntimeException {
    public LoginException() {
        super("Um erro ocorreu ao realizar o login. Por favor, tente novamente.");
    }
}
