package com.sportradar;

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
}
