package com.nielsen.hangman.game.api;

import com.nielsen.hangman.game.api.dto.HunchDTO;
import com.nielsen.hangman.game.model.Hangman;
import com.nielsen.hangman.game.service.HangmanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/hangman")
public class HangmanGameController {

    private final HangmanService hangmanService;

    public HangmanGameController(HangmanService hangmanService) {
        this.hangmanService = hangmanService;
    }

    @PostMapping(path = "/start")
    @ResponseStatus(HttpStatus.OK)
    public Hangman startGame() {
        return hangmanService.startGame();
    }

    @PostMapping(path = "/character")
    @ResponseStatus(HttpStatus.OK)
    public Hangman existsLetter(@RequestBody @Valid HunchDTO hunchDTO) {
        return hangmanService.existsCharacter(hunchDTO.getCharacter(), hunchDTO.getIdentifier());
    }

    @PostMapping(path = "/shot")
    @ResponseStatus(HttpStatus.OK)
    public Hangman tryShot(@RequestBody HunchDTO hunchDTO) {
        return hangmanService.tryHit(hunchDTO.getShot(), hunchDTO.getIdentifier());
    }
}
