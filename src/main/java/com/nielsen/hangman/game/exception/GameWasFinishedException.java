package com.nielsen.hangman.game.exception;

public class GameWasFinishedException extends RuntimeException {
    public GameWasFinishedException(String message) {
        super(message);
    }
}