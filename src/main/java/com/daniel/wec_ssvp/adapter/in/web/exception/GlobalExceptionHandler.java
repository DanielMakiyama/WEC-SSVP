package com.daniel.wec_ssvp.adapter.in.web.exception;

import com.daniel.wec_ssvp.adapter.in.web.dto.ErrorResponseDTO;
import com.daniel.wec_ssvp.domain.exception.CredenciaisInvalidasException;
import com.daniel.wec_ssvp.domain.exception.RecursoJaExisteException;
import com.daniel.wec_ssvp.domain.exception.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Traduz as exceções do domínio para respostas HTTP.
 * O núcleo lança exceções de negócio; só aqui elas viram status HTTP.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ==============================
    // NOT FOUND (404)
    // ==============================
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request) {

        return resposta(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), request, false);
    }

    // ==============================
    // CONFLITO (409) - registro duplicado
    // ==============================
    @ExceptionHandler(RecursoJaExisteException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(
            RecursoJaExisteException ex,
            HttpServletRequest request) {

        return resposta(HttpStatus.CONFLICT, "CONFLICT", ex.getMessage(), request, false);
    }

    // ==============================
    // LOGIN INVÁLIDO (401)
    // ==============================
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErrorResponseDTO> handleCredenciaisInvalidas(
            CredenciaisInvalidasException ex,
            HttpServletRequest request) {

        return resposta(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", ex.getMessage(), request, false);
    }

    // ==============================
    // ACESSO NEGADO (403) - @PreAuthorize
    // ==============================
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return resposta(HttpStatus.FORBIDDEN, "FORBIDDEN",
                "Acesso negado, seu usuário não tem autorização para esta ação", request, false);
    }

    // ==============================
    // VALIDATION ERROR (400)
    // ==============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return resposta(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message, request, false);
    }

    // ==============================
    // ERRO GENÉRICO (500)
    // ==============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(
            Exception ex,
            HttpServletRequest request) {

        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                ex.getMessage(), request, true);
    }

    private ResponseEntity<ErrorResponseDTO> resposta(
            HttpStatus status,
            String erro,
            String mensagem,
            HttpServletRequest request,
            boolean erroInterno) {

        if (erroInterno) {
            log.error("[{}] method={} path={} message={}",
                    erro, request.getMethod(), request.getRequestURI(), mensagem);
        } else {
            log.warn("[{}] method={} path={} message={}",
                    erro, request.getMethod(), request.getRequestURI(), mensagem);
        }

        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagem,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }
}
