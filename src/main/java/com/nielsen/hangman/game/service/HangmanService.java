package com.nielsen.hangman.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nielsen.hangman.game.exception.EntityNotFoundException;
import com.nielsen.hangman.game.exception.GameWasFinishedException;
import com.nielsen.hangman.game.exception.InsufficientAttemptsException;
import com.nielsen.hangman.game.model.Hangman;
import com.nielsen.hangman.game.repository.HangmanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HangmanService {

    private final WordService wordService;
    private final HangmanRepository hangmanRepository;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(HangmanService.class);

    public HangmanService(WordService wordService, HangmanRepository hangmanRepository, ObjectMapper objectMapper) {
        this.wordService = wordService;
        this.hangmanRepository = hangmanRepository;
        this.objectMapper = objectMapper;
    }

    public Hangman startGame() {
        var hangman = new Hangman(wordService.getRandomWord());
        hangman = hangmanRepository.save(hangman);

        try {
            logger.info("Game Started: {}", objectMapper.writeValueAsString(hangman));
        } catch (JsonProcessingException e) {
            logger.error("Failed to log hangman object. Identifier: {}", hangman.getIdentifier());
        }

        return hangman;
    }

    public Hangman existsCharacter(Character hunchChar, UUID identifier) {
        logger.info("Exists Character? Identifier: {}, Letter: {}.", identifier, hunchChar);

        var hangman = hangmanRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException(Hangman.class));

        if(hangman.isFinished()) {
            var finishedGameMessage = "Game already completed!";
            logger.info(finishedGameMessage);
            throw new GameWasFinishedException(finishedGameMessage);
        }

        if(hangman.getRemainingAttempts() < 1) {
            var insufficientAttemptsMsg = "Number of failed attempts. Last status: ("+ hangman.getPlayerWord() + ")";
            logger.info(insufficientAttemptsMsg);

            throw new InsufficientAttemptsException(insufficientAttemptsMsg);
        }

        var characters = hangman.getWord().toCharArray();
        var templateArray = hangman.getPlayerWord().toCharArray();
        var isHit = false;
        var index = 0;
        for(Character character: characters) {
            if(templateArray[index] == '-' && isEqualChar(character, hunchChar)){
                templateArray[index] = character;
                hangman.setCorrectLetters(hangman.getCorrectLetters() + 1);
                isHit = true;
            }
            index ++;
        }

        if(!isHit) {
            logger.info("Player hit letter: {}", hunchChar);
            hangman.setRemainingAttempts(hangman.getRemainingAttempts() - 1);
        }
        hangman.setPlayerWord(String.valueOf(templateArray));
        hangman.addPlayerLetter(hunchChar);
        hangman.setWin(isplayerWon(hangman));

        if(hangman.getRemainingAttempts() == 0) {
            logger.info("exhausted attempts");
            hangman.setAnswer(hangman.getWord());
        }

        try {
            logger.info("Current Game Status: {}", objectMapper.writeValueAsString(hangman));
        } catch (JsonProcessingException e) {
            logger.error("Failed to log hangman object. Identifier: {}", hangman.getIdentifier());
        }

        return hangmanRepository.save(hangman);

    }

    private boolean isplayerWon(Hangman hangman) {
        var isWon = hangman.getWord().length() == hangman.getCorrectLetters();
        logger.info("is player won: {}", isWon);
        return isWon;
    }

    private boolean isEqualChar(Character original, Character simile) {
        return Character.toLowerCase(original) == Character.toLowerCase(simile);
    }

    public Hangman tryHit(String shot, UUID identifier) {
        logger.info("Player try hit word: {}. Identifier: {}", shot, identifier);

        var hangman = hangmanRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException(Hangman.class));

        if(hangman.isFinished()) {
            throw new GameWasFinishedException("game already completed!");
        }

        if(hangman.getWord().equalsIgnoreCase(shot.toUpperCase())) {
            hangman.setWin(true);
            hangman.setPlayerWord(hangman.getWord());
        } else {
            hangman.setWin(false);
            hangman.setAnswer(hangman.getWord());
        }
        hangman.setFinished(true);
        hangman.setRemainingAttempts(0);

        try {
            logger.info("Current Game Status: {}", objectMapper.writeValueAsString(hangman));
        } catch (JsonProcessingException e) {
            logger.error("Failed to log hangman object. Identifier: {}", hangman.getIdentifier());
        }

        return hangmanRepository.save(hangman);
    }
}
