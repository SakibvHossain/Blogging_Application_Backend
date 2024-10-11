package com.example.blogging_application_api.exception.global;

import com.example.blogging_application_api.exception.CategoryNotFoundException;
import com.example.blogging_application_api.exception.ResourceNotFoundException;
import com.example.blogging_application_api.exception.UserExistException;
import com.example.blogging_application_api.exception.model.ExceptionDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(UserExistException.class)
//    public ResponseEntity<ExceptionDetail> accountHolderNotFoundException(UserExistException holderNotFound, WebRequest request) {
//        ExceptionDetail details = new ExceptionDetail(
//                LocalDateTime.now(),
//                holderNotFound.getMessage(),
//                "User Already Exist"
//        );
//        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetail> resourceNotFoundException(ResourceNotFoundException resourceNotFound, WebRequest request) {
        ExceptionDetail details = new ExceptionDetail(
                LocalDateTime.now(),
                resourceNotFound.getMessage(), // This will now include the ID
                request.getDescription(false), // Include the request path
                HttpStatus.NOT_FOUND.value()    // HTTP status code
        );
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND); // Ensure the status is NOT_FOUND
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDetail> categoryNotFoundException(CategoryNotFoundException categoryNotFound, WebRequest request) {
        ExceptionDetail details = new ExceptionDetail(
                LocalDateTime.now(),
                categoryNotFound.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    // Handling customized validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
