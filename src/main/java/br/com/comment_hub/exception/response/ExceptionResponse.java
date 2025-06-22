package br.com.comment_hub.exception.response;

public record ExceptionResponse(String timestamp,
                                String status,
                                String error,
                                String message,
                                String path) {
}