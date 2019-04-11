# hangman-game
Hangman is a game in which the player has to hit which is the proposed word, having as a tip the number of letters. The player initially has 6 attempts. If the player hits the word the number of attempts remains, if the player misses the word the number of attempts is decremented.

## Setup

#### Prerequisites
- [OpenJDK 11](http://jdk.java.net/11/)
- [Maven 3.6+](https://maven.apache.org/install.html) or [IntelliJ IDEA](https://www.jetbrains.com/help/idea/install-and-set-up-product.html)

#### Running

- Before running application is required configure Postgres database on port 5432 and create database 'hangman'
- If you prefer run database into [Docker](https://docs.docker.com/install/linux/docker-ee/ubuntu/):
```
docker run --name hangman -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=hangman postgres:9.6-alpine
```
After this you can run the application:
```
mvn clean compile spring-boot:run
```


## Instructions

To start the game:
```
POST /hangman/start

return:
{
    "id": 1,
    "playerWord": "--------",
    "playerLetters": "",
    "wordLength": 8,
    "identifier": "bd6c56c7-c886-4ec4-8b11-8b34e9f1836f",
    "correctLetters": 0,
    "win": false,
    "remainingAttempts": 6,
    "finished": false,
    "answer": ""
}
```

To hit the letter:
- You need to pass the identifier returned by /hangman/start
```
POST /hangman/character
{
	"identifier": "bd6c56c7-c886-4ec4-8b11-8b34e9f1836f",
	"character": "a"
}

return:
{
    "id": 1,
    "playerWord": "-a-a---a",
    "playerLetters": "a, ",
    "wordLength": 8,
    "identifier": "bd6c56c7-c886-4ec4-8b11-8b34e9f1836f",
    "correctLetters": 3,
    "win": false,
    "remainingAttempts": 6,
    "finished": false,
    "answer": ""
}
```

If the player already knows the word he can try to hit it directly, passing the word complete:

```
POST /hangman/shot
{
	"identifier": "bd6c56c7-c886-4ec4-8b11-8b34e9f1836f",
	"shot": "caravela"
}

return:
{
    "id": 1,
    "playerWord": "Caravela",
    "playerLetters": "a, ",
    "wordLength": 8,
    "identifier": "bd6c56c7-c886-4ec4-8b11-8b34e9f1836f",
    "correctLetters": 3,
    "win": true,
    "remainingAttempts": 0,
    "finished": true,
    "answer": ""
}
```

If the player does not hit the word, the *answer* property will come with the correct answer.