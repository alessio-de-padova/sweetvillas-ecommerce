package com.nimesia.sweetvillas.handlers;

import java.util.ArrayList;

import com.nimesia.sweetvillas.bean.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ArrayList errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            errors.add(
                    new ApiError()
                            .builder(
                                    error.getDefaultMessage(),
                                    ((FieldError) error).getField(),
                                    (String) ((FieldError) error).getRejectedValue()
                            )
            );
        });
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }
}