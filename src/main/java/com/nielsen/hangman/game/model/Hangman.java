package com.nielsen.hangman.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Hangman extends BaseEntity {
    @JsonIgnore
    private String word;
    private String playerWord;
    @ElementCollection
    private List<Character> playerLetters;
    private int wordLength;
    private UUID identifier;
    private int correctLetters;
    private boolean win;
    private int remainingAttempts;
    private boolean finished;
    private String answer;

    Hangman () { }
    public Hangman(String word) {
        this.word = word;
        this.wordLength = word.length();
        this.playerLetters = new ArrayList<>();
        this.identifier = UUID.randomUUID();
        this.correctLetters = 0;
        this.win = false;
        this.remainingAttempts = 6;
        this.finished = false;
        this.answer = "";

        playerWord = initializePlayerWordTemplate(word);
    }

    private String initializePlayerWordTemplate(String word) {
        String template = "";
        for(int i = 0; i< word.length(); i ++){
            template += "-";
        }
        return template;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPlayerWord() {
        return playerWord;
    }

    public void setPlayerWord(String playerWord) {
        this.playerWord = playerWord;
    }

    public List<Character> getPlayerLetters() {
        return playerLetters;
    }

    public void setPlayerLetters(List<Character> playerLetters) {
        this.playerLetters = playerLetters;
    }

    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public void addPlayerLetter(Character character) {
        this.playerLetters.add(character);
    }

    public int getCorrectLetters() {
        return correctLetters;
    }

    public void setCorrectLetters(int correctLetters) {
        this.correctLetters = correctLetters;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
