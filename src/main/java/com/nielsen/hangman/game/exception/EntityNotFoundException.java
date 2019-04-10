package com.nielsen.hangman.game.exception;

import java.lang.reflect.Type;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Type type) {
        super(((Class) type).getSimpleName() + " not found.");
    }
}