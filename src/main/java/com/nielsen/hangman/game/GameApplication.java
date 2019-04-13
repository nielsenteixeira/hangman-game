package com.nielsen.hangman.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GameApplication {
	private static final Logger logger = LoggerFactory.getLogger(GameApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);

		logger.info("Hangman game has started.");
	}

}
