package com.nikolastojanovic.rbtticketingsystem.application.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.ErrorResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException ex) {
    log.error(ex.getMessage());
    var errorResponse = new ErrorResponse(ex.getError().name(), ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getError().getStatus()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
    return handleException(ex, "VALIDATION_ERROR", 400);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleException(DataIntegrityViolationException ex) {
    return handleException(ex, "DATA_INTEGRITY_ERROR", 500);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    return handleException(ex, "UNKNOWN_ERROR", 500);
  }

  private ResponseEntity<?> handleException(Exception ex, String error, int code) {
    log.error(ex.getMessage());
    var errorResponse = new ErrorResponse(error, ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(code));
  }
}
