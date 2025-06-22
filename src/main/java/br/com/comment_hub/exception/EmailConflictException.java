package br.com.comment_hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailConflictException extends RuntimeException {
    public EmailConflictException() {
        super("Email already exists");
    }
}
