package com.sportradar;

import com.sportradar.exceptions.MatchNotFoundException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scoreboard {

    private final Map<String, Match> matches = new HashMap<>();
    private static final Comparator<Match> SUMMARY_COMPARATOR = Comparator
            .comparingInt(Match::getTotalScore).reversed()
            .thenComparing(Comparator.comparing(Match::getStartTime).reversed());

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

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative.");
        }

        homeTeam = normalizeTeamName(homeTeam);
        awayTeam = normalizeTeamName(awayTeam);

        String matchName = getMatchName(homeTeam, awayTeam);
        Match match = matches.get(matchName);
        if (match == null) {
            throw new MatchNotFoundException("Match not found.");
        }

        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        homeTeam = normalizeTeamName(homeTeam);
        awayTeam = normalizeTeamName(awayTeam);
        String key = getMatchName(homeTeam, awayTeam);
        if (!matches.containsKey(key)) throw new MatchNotFoundException("Match not found.");
        matches.remove(key);
    }

    public List<Match> getSummary() {
        return matches.values().stream()
                .sorted(SUMMARY_COMPARATOR)
                .collect(Collectors.toList());
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
