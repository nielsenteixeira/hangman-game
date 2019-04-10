package com.nielsen.hangman.game.repository;

import com.nielsen.hangman.game.model.Hangman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HangmanRepository extends JpaRepository<Hangman, Long> {
    Optional<Hangman> findByIdentifier(UUID identifier);
}
