package br.com.comment_hub.exception.handler;

import org.springframework.http.HttpStatus;

public record ExceptionConfig(String message, HttpStatus status, boolean shouldLog) {
}