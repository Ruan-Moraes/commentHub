package br.com.comment_hub.exception.handler;

import br.com.comment_hub.exception.EmailConflictException;
import br.com.comment_hub.exception.TokenExpection;
import br.com.comment_hub.exception.response.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice()
public class GlobalExceptionHandler {
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);

        return errorResponse;
    }

    @ExceptionHandler(EmailConflictException.class)
    public final ResponseEntity<ExceptionResponse> EmailConflictException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.CONFLICT),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(TokenExpection.class)
    public final ResponseEntity<ExceptionResponse> handleTokenException(TokenExpection e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.UNAUTHORIZED),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(new HttpHeaders())
                .body(getErrorsMap(errors));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
