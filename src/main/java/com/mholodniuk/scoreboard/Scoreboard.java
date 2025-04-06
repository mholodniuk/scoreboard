package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.ScoreboardSummary;
import com.mholodniuk.scoreboard.vo.Team;

import java.time.LocalDateTime;
import java.util.*;

import static com.mholodniuk.scoreboard.util.NullSafetyUtils.requireNonNull;

public class Scoreboard {
    private final Map<Game, LocalDateTime /* Insertion time */> trackedGames = new HashMap<>();
    private final Set<Team> playingTeams = new HashSet<>();

    public Game startGame(Team homeTeam, Team awayTeam) {
        requireNonNull("Teams must not be null", homeTeam, awayTeam);
        if (Objects.equals(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("You cannot start a game with the same team");
        }
        if (playingTeams.contains(homeTeam) || playingTeams.contains(awayTeam)) {
            throw new IllegalStateException("One or both teams are already playing");
        }
        Game game = new Game(homeTeam, awayTeam);
        trackedGames.put(game, LocalDateTime.now());
        playingTeams.addAll(List.of(homeTeam, awayTeam));
        return game;
    }

    public void updateGameScore(Game game, Score score) {
        requireNonNull("Game must not be null", game);
        requireNonNull("Score must not be null", score);
        if (trackedGames.containsKey(game)) {
            game.updateScore(score);
        }
    }

    public void finishGame(Game game) {
        requireNonNull("Game must not be null", game);
        var insertionTime = trackedGames.remove(game);
        if (insertionTime != null) {
            game.teams().forEach(playingTeams::remove);
        }
    }

    public ScoreboardSummary collectSummary() {
        var orderedGames = trackedGames.entrySet().stream()
                .sorted(new GameAndInsertionTimeComparator())
                .map(Map.Entry::getKey)
                .toList();

        return ScoreboardSummary.of(orderedGames);
    }

    private static class GameAndInsertionTimeComparator implements Comparator<Map.Entry<Game, LocalDateTime>> {
        @Override
        public int compare(Map.Entry<Game, LocalDateTime> game1, Map.Entry<Game, LocalDateTime> game2) {
            int scoreComparison = Integer.compare(game2.getKey().score().total(), game1.getKey().score().total());

            if (scoreComparison != 0) {
                return scoreComparison;
            }

            return game2.getValue().compareTo(game1.getValue());
        }
    }
}
