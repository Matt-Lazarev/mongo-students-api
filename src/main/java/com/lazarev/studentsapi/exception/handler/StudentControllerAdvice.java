package com.lazarev.studentsapi.exception.handler;

import com.lazarev.studentsapi.exception.custom.StudentNotFoundException;
import com.lazarev.studentsapi.exception.custom.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class StudentControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StudentNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<?> handleStudentNotFoundException(RuntimeException ex){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.NOT_FOUND.name());
        errors.put("error", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errors);
    }

    @ExceptionHandler(io.jsonwebtoken.SignatureException.class)
    public ResponseEntity<?> handleSignatureExceptionException(io.jsonwebtoken.SignatureException ex){
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", HttpStatus.BAD_REQUEST.name());
        errors.put("error", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errors);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("timestamp", LocalDateTime.now().toString());
        errors.put("status", status.name());
        errors.put("error", "method arguments not valid");

        ex.getBindingResult().getAllErrors().forEach(
                error -> errors.put(error.getObjectName(), error.getDefaultMessage()));

        return ResponseEntity
                .status(status)
                .body(errors);
    }
}
