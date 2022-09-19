package com.example.usersservice.exceptionHandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolationException(
      final DataIntegrityViolationException ex, WebRequest request) {
    return handleInternal(ex, HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(EntityExistsException.class)
  protected ResponseEntity<Object> handleEntityExistException(
      final EntityExistsException ex, final WebRequest request) {
    return handleInternal(ex, HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  protected ResponseEntity<Object> handleEmptyResultDataAccessException(
      final EmptyResultDataAccessException ex, final WebRequest request) {
    return handleInternal(ex, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFoundException(
      final EntityNotFoundException ex, final WebRequest request) {
    return handleInternal(ex, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgumentException(
      final IllegalArgumentException ex, final WebRequest request) {
    return handleInternal(ex, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolationException(
      final ConstraintViolationException ex, final WebRequest request) {
    return handleInternal(ex, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(NullPointerException.class)
  protected ResponseEntity<Object> handleNullPointerException(
      final NullPointerException ex, final WebRequest request) {
    return handleInternal(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }



  private ResponseEntity<Object> handleInternal(Exception ex,HttpStatus status,WebRequest request){
    return this.handleExceptionInternal(ex,null,this.copyHeaders(request),status,request);

  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {

    List<ValidationFieldError> validationFieldErrorList = new ArrayList<>();
    for (FieldError error: ex.getBindingResult().getFieldErrors()) {
      ValidationFieldError fieldError = new ValidationFieldError();
      fieldError.setError(error.getDefaultMessage());
      fieldError.setField(error.getField());
      validationFieldErrorList.add(fieldError);
    }

    ValidationError validationError = new ValidationError();

    validationError.setValidationError(validationFieldErrorList);
    validationError.setTime(LocalDateTime.now());
    validationError.setCode(status.value());
    validationError.setMessage(ex.getMessage());

    return new ResponseEntity<>(validationError,new HttpHeaders(),HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                           HttpHeaders headers, HttpStatus status,
                                                           WebRequest request) {
    Error error = new Error();
    error.setCode(status.value());
    error.setTime(LocalDateTime.now());
    error.setMessage(ex.getMessage());
    return new ResponseEntity<>(error,new HttpHeaders(),status);
  }

  private HttpHeaders copyHeaders(final WebRequest request){
    HttpHeaders headers = new HttpHeaders();
    Iterator<String> iterator = request.getHeaderNames();

    while (iterator.hasNext()){
      String headerName = iterator.next();
      String headerValue = request.getHeader(headerName);
      headers.add(headerName,headerValue);

    }
      return headers;
  }

}
