package com.nielsen.hangman.game.service;

import com.nielsen.hangman.game.exception.EntityNotFoundException;
import com.nielsen.hangman.game.exception.GameWasFinishedException;
import com.nielsen.hangman.game.exception.InsufficientAttemptsException;
import com.nielsen.hangman.game.model.Hangman;
import com.nielsen.hangman.game.repository.HangmanRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class HangmanService {

    private final HangmanRepository hangmanRepository;

    public HangmanService(HangmanRepository hangmanRepository) {
        this.hangmanRepository = hangmanRepository;
    }

    public Hangman startGame(){
        return hangmanRepository.save(new Hangman(getRandomWord()));
    }

    private String getRandomWord(){
        int rnd = new Random().nextInt(getWords().size());
        return getWords().get(rnd);
    }

    private List<String> getWords(){
        return Arrays.asList("Bonito", "Mochila", "Parafuso", "Marrom", "Caravela", "Cantil", "Moeda", "Planilha", "Quadrilha", "Penteadeira", "Cuia",
                "Calças", "Faca", "Cinco", "Familia", "Chocolate", "Janela", "Sete", "Microfone", "Napolitano", "Carnauba", "Guitarra");
    }

    public Hangman existsCharacter(Character hunchChar, UUID identifier) {
        var hangman = hangmanRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException(Hangman.class));

        if(hangman.isFinished()) {
            throw new GameWasFinishedException("Game already completed!");
        }

        if(hangman.getRemainingAttempts() < 1) {
            throw new InsufficientAttemptsException("Number of failed attempts. Last status: ("+ hangman.getPlayerWord() + ")");
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
            hangman.setRemainingAttempts(hangman.getRemainingAttempts() - 1);
        }
        hangman.setPlayerWord(String.valueOf(templateArray));
        hangman.addPlayerLetter(hunchChar);
        hangman.setWin(isplayerWhon(hangman));

        if(hangman.getRemainingAttempts() == 0) {
            hangman.setAnswer(hangman.getWord());
        }

        return hangmanRepository.save(hangman);

    }

    private boolean isplayerWhon(Hangman hangman) {
        return hangman.getWord().length() == hangman.getCorrectLetters();
    }

    private boolean isEqualChar(Character original, Character simile){
        return Character.toLowerCase(original) == Character.toLowerCase(simile);
    }

    public Hangman tryHit(String shot, UUID identifier) {
        var hangman = hangmanRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException(Hangman.class));

        if(hangman.isFinished()) {
            throw new GameWasFinishedException("ame already completed!");
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

        return hangmanRepository.save(hangman);
    }
}
