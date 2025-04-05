package com.sportradar;

import java.util.LinkedHashMap;
import java.util.Map;

public class Scoreboard {

    private final Map<String, Match> matches = new LinkedHashMap<>();

    public void startMatch(String homeTeam, String awayTeam) {
        homeTeam = normalizeTeamName(homeTeam);
        awayTeam = normalizeTeamName(awayTeam);

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Home team and away team cannot be the same.");
        }

        String matchName = getMatchName(homeTeam, awayTeam);
        if (matches.containsKey(matchName)) {
            throw new IllegalArgumentException("Match already started.");
        }

        matches.put(matchName, new Match(homeTeam, awayTeam));
    }

    private String getMatchName(String homeTeam, String awayTeam) {
        return homeTeam + "-" + awayTeam;
    }

    private String normalizeTeamName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Team cannot be null.");
        }
        String cleanedName = name.trim().toLowerCase();
        if (cleanedName.isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be empty.");
        }
        return Character.toUpperCase(cleanedName.charAt(0)) + cleanedName.substring(1);
    }
}
