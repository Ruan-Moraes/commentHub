package br.com.comment_hub.exception.handler;

import br.com.comment_hub.exception.*;
import br.com.comment_hub.exception.response.ExceptionResponse;
import br.com.comment_hub.model.logs.ErrorLog;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice()
public class GlobalExceptionHandler {
    private final br.com.comment_hub.repository.ErrorLogRepository errorLogRepository;

    public GlobalExceptionHandler(br.com.comment_hub.repository.ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();

        errorResponse.put("errors", errors);

        return errorResponse;
    }

    private String stackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        t.printStackTrace(pw);

        String full = sw.toString();
        String[] lines = full.split("\\R");

        int limit = Math.min(lines.length, 6);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < limit; i++) {
            sb.append(lines[i]);

            if (i < limit - 1) sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @ExceptionHandler(EmailConflictException.class)
    public final ResponseEntity<ExceptionResponse> emailConflictException(EmailConflictException e, WebRequest request) {
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
    public final ResponseEntity<ExceptionResponse> tokenException(TokenExpection e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.BAD_REQUEST),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(LoginException.class)
    public final ResponseEntity<ExceptionResponse> loginException(LoginException e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.BAD_REQUEST),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.UNAUTHORIZED),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<ExceptionResponse> authorizationDeniedException(AuthorizationDeniedException e, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(
//                new Date().toString(),
//                String.valueOf(HttpStatus.UNAUTHORIZED),
//                e.getClass().getSimpleName(),
//                e.getMessage(),
//                request.getDescription(false)
//        );
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
//    }

    @ExceptionHandler(RegistrationConflictException.class)
    public final ResponseEntity<ExceptionResponse> registrationConflictException(RegistrationConflictException e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.CONFLICT),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> notFoundException(NotFoundException e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(HttpStatus.NOT_FOUND),
                e.getClass().getSimpleName(),
                e.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
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
        Map<String, ExceptionConfig> exceptionConfigs = Map.of(
                "AuthorizationDeniedException", new ExceptionConfig("Não autorizado!", HttpStatus.UNAUTHORIZED, false),
                "ValidationException", new ExceptionConfig("Dados inválidos!", HttpStatus.BAD_REQUEST, true)
        );

        String exceptionName = e.getClass().getSimpleName();

        ExceptionConfig config = exceptionConfigs.getOrDefault(
                exceptionName,
                new ExceptionConfig("Ops, ocorreu um erro inesperado, estamos trabalhando para resolver isso. Por favor, tente novamente mais tarde.", HttpStatus.INTERNAL_SERVER_ERROR, true)
        );

        if (config.shouldLog()) {
            ErrorLog errorLog = ErrorLog.builder()
                    .createdAt(LocalDateTime.now())
                    .message(Objects.toString(e.getMessage(), e.getClass().getSimpleName()))
                    .stacktrace(stackTraceToString(e))
                    .status(String.valueOf(config.status().value()))
                    .build();

            this.errorLogRepository.save(errorLog);
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date().toString(),
                String.valueOf(config.status().value()),
                e.getClass().getSimpleName(),
                config.message(),
                request.getDescription(false)
        );

        return ResponseEntity.status(config.status()).body(exceptionResponse);
    }
}
