# Live Football World Cup Scoreboard Library

This is a simple Java library implementation of a **Live Football World Cup Scoreboard**

## Features

- Start a new match with an initial score of 0 - 0.
- Update the score of an existing match.
- Finish a match (remove it from the scoreboard).
- Display a summary of matches in progress, ordered by:
    - Total score (descending).
    - Most recently started match (for matches with the same total score).

## Build the Project

To build the project, use:

```bash
mvn clean install
```

## Usage example
In this example, we will create a scoreboard, start two matches, update their scores, print the summary and finish those matches.

```java
import com.sportradar.Scoreboard;

public class Main {
  public static void main(String[] args) {
    Scoreboard scoreboard = new Scoreboard();

    scoreboard.startMatch("Mexico", "Canada");
    scoreboard.startMatch("Spain", "Brazil");

    scoreboard.updateScore("Mexico", "Canada", 0, 5);
    scoreboard.updateScore("Spain", "Brazil", 2, 2);

    scoreboard.getSummary().forEach(System.out::println);

    scoreboard.finishMatch("Mexico", "Canada");
    scoreboard.finishMatch("Spain", "Brazil");
  }
}
```

## Design Decisions

- **In-memory store**: Used `HashMap` to store matches by their name.
- **TDD**: Followed test-driven development for each feature.
- **Object-Oriented Design**: Encapsulated match data into a `Match` class and scoreboard logic into a `Scoreboard` class.
- **SOLID Principles**: Separated logic, used exceptions for error cases.
- **Edge Cases Handled**:
    - Duplicate match start attempts.
    - Start match with a team that is already playing.
    - Score updates or finish requests for non-existent matches.
    - Invalid score updates (negative values).
    - Invalid match names (empty strings or nulls).
    - Team names are trimmed and lowercased (with first letter capitalized) to ensure consistency.

## Tests

Tests are written using **JUnit 5** and cover:
- Starting matches
- Updating scores
- Finishing matches
- Summary sorting

Run tests with:

```bash
mvn clean test
```

## Assumptions
- A match is uniquely identified by a pair of team names (home + away).
- There is no historical data stored.
- Start time is automatically captured when the match is started.
