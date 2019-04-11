CREATE TABLE hangman
(
    id SERIAL PRIMARY KEY,
    created_at timestamp with time zone NOT NULL,
    modified_at timestamp with time zone NOT NULL,
    word character varying(100) NOT NULL,
    player_word character varying(100) NOT NULL,
    player_letters character varying(100) NULL,
    word_length integer NOT NULL,
    "identifier" uuid NOT NULL,
    correct_letters integer NOT NULL,
    win boolean NOT NULL,
    remaining_attempts integer NOT NULL,
    finished boolean NOT NULL,
    answer character varying(100) NOT NULL
);