package com.nielsen.hangman.game.service;

import com.nielsen.hangman.game.exception.WordsNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Service
public class WordService {

    private final String wordsPath;

    public WordService(@Value("${game.words}") final String wordsPath) {
        this.wordsPath = wordsPath;
    }

    public String getRandomWord(){
        int rnd = new Random().nextInt(getWords().size());
        return getWords().get(rnd);
    }

    private List<String> getWords() {
        try {
            return Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(wordsPath).getFile()));
        } catch (IOException e) {
            throw new WordsNotFoundException("No words available for the game.");
        }
    }
}
