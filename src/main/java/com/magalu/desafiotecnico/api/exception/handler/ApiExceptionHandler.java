package com.magalu.desafiotecnico.api.exception.handler;

import com.magalu.desafiotecnico.api.exception.ApiException;
import com.magalu.desafiotecnico.api.exception.model_error.ApiError;
import com.magalu.desafiotecnico.domain.exception.DomainException;
import com.magalu.desafiotecnico.infrastructure.exception.InfraException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

  public static final String DOMAIN_ERROR_TYPE = "domain";
  public static final String API_ERROR_TYPE = "api";
  public static final String INFRA_ERROR_TYPE = "infra";
  public static final String OTHER_ERROR_TYPE = "outros";

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ApiError> handle(DomainException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiError.builder()
            .tipo(DOMAIN_ERROR_TYPE)
            .mensagem(ex.getMessage())
            .build());
  }

  @ExceptionHandler(InfraException.class)
  public ResponseEntity<ApiError> handle(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiError.builder()
            .tipo(INFRA_ERROR_TYPE)
            .mensagem(ex.getMessage())
            .build());
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiError> handle(ApiException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiError.builder()
            .tipo(API_ERROR_TYPE)
            .mensagem(ex.getMessage())
            .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handle(MethodArgumentNotValidException ex) {
    log.error(ex.getMessage(), ex);

    var errors = ex.getBindingResult().getFieldErrors().stream()
        .map(e -> "campo->" + e.getField().concat(" - ")
            .concat(Objects.requireNonNull(e.getDefaultMessage())))
        .collect(Collectors.toList()).toString();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiError.builder()
            .tipo(API_ERROR_TYPE)
            .mensagem(errors)
            .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handle(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiError.builder()
            .tipo(OTHER_ERROR_TYPE)
            .mensagem(ex.getMessage())
            .build());
  }

}
