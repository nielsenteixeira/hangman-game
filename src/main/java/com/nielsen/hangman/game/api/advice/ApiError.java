package com.nielsen.hangman.game.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiError {
 
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private int code;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
        this.code = status.value();
    }

    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
        this.code = status.value();
    }

    public ApiError(HttpStatus status, Errors validationErrors) {
        this.status = status;
        this.message = "Validation error";
        this.code = status.value();
        this.errors = new ArrayList<>();

        for (var error: validationErrors.getAllErrors()) {
            this.errors.add(error.getDefaultMessage());
        }
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}