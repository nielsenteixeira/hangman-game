package com.nielsen.hangman.game.exception;

public class InsufficientAttemptsException extends RuntimeException {
    public InsufficientAttemptsException(String message) {
        super(message);
    }
}