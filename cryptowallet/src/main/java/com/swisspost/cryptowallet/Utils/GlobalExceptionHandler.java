package com.swisspost.cryptowallet.Utils;

import com.swisspost.cryptowallet.Dtos.CreateWalletResponse;
import com.swisspost.cryptowallet.Dtos.GlobalExceptionHandlerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalExceptionHandlerResponse> handleValidationException(ValidationException ex) {
        GlobalExceptionHandlerResponse response = new GlobalExceptionHandlerResponse();
        response.setMessage("Validation Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalExceptionHandlerResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        GlobalExceptionHandlerResponse response = new GlobalExceptionHandlerResponse();
        response.setMessage("Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GlobalExceptionHandlerResponse> handleGeneralException(Exception ex) {
        GlobalExceptionHandlerResponse response = new GlobalExceptionHandlerResponse();
        response.setMessage(Constants.SOMETHING_WENT_WRONG + ": " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GlobalExceptionHandlerResponse> handleConstraintViolationException(javax.validation.ConstraintViolationException ex) {
        GlobalExceptionHandlerResponse response = new GlobalExceptionHandlerResponse();
        response.setMessage("Validation failed: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}