package com.sportradar;

import com.sportradar.exceptions.MatchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void testStartMatch_successfullyAddsMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        List<Match> summary = scoreboard.getSummary();

        assertEquals(1, summary.size());
        Match match = summary.get(0);
        assertEquals("Mexico", match.getHomeTeam());
        assertEquals("Canada", match.getAwayTeam());
    }

    @Test
    void testStartMatch_duplicateMatchThrowsException() {
        scoreboard.startMatch("Spain", "Brazil");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Spain", "Brazil"));
    }

    @Test
    void testStartMatch_nullTeamThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(null, "Brazil"));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Spain", null));
    }

    @Test
    void testStartMatch_sameTeamThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Poland", "Poland"));
    }

    @Test
    void testStartMatch_emptyTeamNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("  ", "Brazil"));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Spain", " \n\t"));
    }

    @Test
    void testStartMatch_teamNamesAreTrimmedAndLowercased() {
        scoreboard.startMatch("  MEXICO ", "  cAnAdA ");
        List<Match> summary = scoreboard.getSummary();

        assertEquals(1, summary.size());
        Match match = summary.get(0);
        assertEquals("Mexico", match.getHomeTeam());
        assertEquals("Canada", match.getAwayTeam());
    }

    @Test
    void testGetSummary_noMatchesReturnsEmptyList() {
        List<Match> summary = scoreboard.getSummary();
        assertEquals(0, summary.size());
    }

    @Test
    void testUpdateScore_successfullyUpdatesScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 2, 1);

        Match match = scoreboard.getSummary().get(0);
        assertEquals(2, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
    }

    @Test
    void testUpdateScore_matchNotFoundThrowsException() {
        assertThrows(MatchNotFoundException.class, () -> scoreboard.updateScore("Spain", "Brazil", 1, 0));
    }

    @Test
    void testUpdateScore_negativeScoreThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Belarus", "England", -1, 1));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Belarus", "England", 1, -1));
    }

    @Test
    void testUpdateScore_nullTeamThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(null, "Canada", 2, 1));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Mexico", null, 2, 1));
    }

    @Test
    void testUpdateScore_teamNamesAreTrimmedAndLowercased() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("  MEXICO ", "  cAnAdA ", 2, 1);

        Match match = scoreboard.getSummary().get(0);
        assertEquals(2, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
    }
}
