package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.util.Pair;
import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.ScoreboardSummary;
import com.mholodniuk.scoreboard.vo.Team;

import java.time.LocalDateTime;
import java.util.*;

import static com.mholodniuk.scoreboard.util.CollectionUtils.findFirstMatchingOrNull;
import static com.mholodniuk.scoreboard.util.NullSafetyUtils.requireNonNull;

public class Scoreboard {
    private final List<Pair<Game, LocalDateTime /* Insertion time */>> trackedGames = new ArrayList<>();
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
        trackedGames.add(Pair.of(game, LocalDateTime.now()));
        playingTeams.addAll(List.of(homeTeam, awayTeam));
        return game;
    }

    public void updateGameScore(Game game, Score score) {
        requireNonNull("Game must not be null", game);
        requireNonNull("Score must not be null", score);
        for (var activeGame : activeGames()) {
            if (Objects.equals(game, activeGame)) {
                game.updateScore(score);
                return;
            }
        }
    }

    public void finishGame(Game game) {
        requireNonNull("Game must not be null", game);
        var gameToFinish = findFirstMatchingOrNull(trackedGames, trackedGame -> Objects.equals(game, trackedGame.first()));
        if (trackedGames.remove(gameToFinish)) {
            game.teams().forEach(playingTeams::remove);
        }
    }

    public ScoreboardSummary collectSummary() {
        var orderedGames = trackedGames.stream()
                .sorted(new GameAndInsertionTimeComparator())
                .map(Pair::first)
                .toList();

        return ScoreboardSummary.of(orderedGames);
    }

    private List<Game> activeGames() {
        return trackedGames.stream().map(Pair::first).toList();
    }

    private static class GameAndInsertionTimeComparator implements Comparator<Pair<Game, LocalDateTime>> {
        @Override
        public int compare(Pair<Game, LocalDateTime> game1, Pair<Game, LocalDateTime> game2) {
            int scoreComparison = Integer.compare(game2.first().score().total(), game1.first().score().total());

            if (scoreComparison != 0) {
                return scoreComparison;
            }

            return game2.second().compareTo(game1.second());
        }
    }
}
