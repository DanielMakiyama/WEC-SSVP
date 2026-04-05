package com.daniel.wec_ssvp.exception;

import com.daniel.wec_ssvp.model.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ==============================
    // NOT FOUND (404)
    // ==============================
    @ExceptionHandler({
            AssistidoNaoEncontradoException.class,
            ConferenciaNaoEncontradaException.class,
            ConselhoParticularNaoEncontradoException.class,
            ConferenciaJaExisteException.class,
            ConselhoJaExisteException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request) {

        log.warn(
                "[NOT_FOUND] method={} path={} message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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

        log.warn(
                "[VALIDATION_ERROR] method={} path={} message={}",
                request.getMethod(),
                request.getRequestURI(),
                message
        );

        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    // ==============================
    // ERRO GENÉRICO (500)
    // ==============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(
            Exception ex,
            HttpServletRequest request) {

        log.error(
                "[INTERNAL_ERROR] method={} path={} message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage()
        );

        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}