package com.engineer.batchprocessing.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult ( )
                .getFieldErrors ( )
                .stream ( )
                .map (error -> error.getField () + ": " + error.getDefaultMessage ( ))
                .collect (Collectors.toList ( ));

        Map<String, Object> response = new HashMap<> ();
        response.put ("status", HttpStatus.BAD_REQUEST.value ());
        response.put ("message", "Validation failed");
        response.put ("errors", errors);

        return ResponseEntity.badRequest ().body (response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleValidation(Exception ex) {

        Map<String, Object> response = new HashMap<> ();
        response.put ("status", HttpStatus.INTERNAL_SERVER_ERROR.value ());
        response.put ("message1", ex.getMessage ());
        response.put ("message2", "Something went wrong. Please try again later.");
        response.put ("timestamp", LocalDateTime.now ());

        return ResponseEntity.badRequest ().body (response);
    }
}
