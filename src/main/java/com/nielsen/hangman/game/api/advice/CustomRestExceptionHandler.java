package com.nielsen.hangman.game.api.advice;

import com.nielsen.hangman.game.exception.EntityNotFoundException;
import com.nielsen.hangman.game.exception.GameWasFinishedException;
import com.nielsen.hangman.game.exception.InsufficientAttemptsException;
import com.nielsen.hangman.game.exception.WordsNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getCause().getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {

        var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ InsufficientAttemptsException.class })
    public ResponseEntity<Object> handleInsufficientAttempts(InsufficientAttemptsException ex, WebRequest request) {

        var apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ GameWasFinishedException.class })
    public ResponseEntity<Object> handleGameWasFinished(GameWasFinishedException ex, WebRequest request) {

        var apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ WordsNotFoundException.class })
    public ResponseEntity<Object> handleWordsNotFound(WordsNotFoundException ex, WebRequest request) {

        var apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}