package com.mybank.userservice.base;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AdvisorController extends ResponseEntityExceptionHandler {


    @ExceptionHandler({RecordNotFoundException.class, EmptyResultDataAccessException.class})
    public final ResponseEntity<ResponseError> handleRecordNotFoundException(Exception ex, WebRequest request) {
        ResponseError response = ResponseError.builder().mensaje("Resgistro no encontrado").build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseError response = ResponseError.builder().mensaje(ex.getBindingResult().getFieldError().getDefaultMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public final ResponseEntity<ResponseError> handleConstraintViolationException(Exception ex, WebRequest request) {
        ResponseError response = ResponseError.builder().mensaje("El correo ya registrado").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
