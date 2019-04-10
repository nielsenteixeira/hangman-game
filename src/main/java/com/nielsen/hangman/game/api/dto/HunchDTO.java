package com.nielsen.hangman.game.api.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class HunchDTO {
    @NotNull
        private Character character;

    @NotNull
    private UUID identifier;

    private String shot;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public String getShot() {
        return shot;
    }

    public void setShot(String shot) {
        this.shot = shot;
    }
}
