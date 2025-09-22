package com.nikolastojanovic.rbtticketingsystem.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.ErrorResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error("Custom exception: {}", ex.getMessage());
        var errorResponse = new ErrorResponse(ex.getError().name(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getError().getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        var errorResponse = new ValidationErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        var errorResponse = new ErrorResponse("DATA_INTEGRITY_ERROR", "Data integrity violation occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unknown error: {}", ex.getMessage(), ex);
        var errorResponse = new ErrorResponse("UNKNOWN_ERROR", "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ValidationErrorResponse(
            String error,
            String message,
            Map<String, String> fieldErrors
    ) {}
}