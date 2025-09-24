package com.nikolastojanovic.rbtticketingsystem.application.exception;

import com.nikolastojanovic.rbtticketingsystem.application.constants.StringConstants;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.ErrorResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(TicketingException.class)
    public ResponseEntity<ErrorResponse> handleTicketingException(TicketingException ex) {
        logError(StringConstants.TICKETING_SYSTEM_ERROR, ex.getMessage());

        ErrorResponse errorResponse = createErrorResponse(
                ex.getError().name(),
                ex.getMessage()
        );

        return createResponseEntity(errorResponse, HttpStatus.valueOf(ex.getError().getStatus()));
    }

    @ExceptionHandler({
            InternalAuthenticationServiceException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> handleAuthenticationExceptions(Exception ex) {
        logError(StringConstants.INVALID_CREDENTIALS, ex.getMessage());

        ErrorResponse errorResponse = createErrorResponse(
                StringConstants.INVALID_CREDENTIALS,
                StringConstants.VALIDATION_FAILED_MESSAGE
        );

        return createResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        logError(StringConstants.VALIDATION_FAILED_MESSAGE, ex.getMessage());

        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        ValidationErrorResponse errorResponse = createValidationErrorResponse(
                StringConstants.VALIDATION_FAILED_MESSAGE,
                StringConstants.VALIDATION_FAILED_MESSAGE,
                fieldErrors
        );

        return createResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());

        ErrorResponse errorResponse = createErrorResponse(
                StringConstants.DATA_INTEGRITY_ERROR_CODE,
                StringConstants.DATA_INTEGRITY_ERROR_MESSAGE
        );

        return createResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unknown error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = createErrorResponse(
                StringConstants.UNKNOWN_ERROR_CODE,
                StringConstants.UNKNOWN_ERROR_MESSAGE
        );

        return createResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(String errorType, String message) {
        log.error("{}: {}", errorType, message);
    }

    private ErrorResponse createErrorResponse(String code, String message) {
        return new ErrorResponse(code, message);
    }

    private ValidationErrorResponse createValidationErrorResponse(String code, String message, Map<String, String> errors) {
        return new ValidationErrorResponse(code, message, errors);
    }

    private <T> ResponseEntity<T> createResponseEntity(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}