package com.nielsen.hangman.game.exception;

public class WordsNotFoundException extends RuntimeException {
    public WordsNotFoundException(String message) {
        super(message);
    }
}