package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.ScoreboardSummary;
import com.mholodniuk.scoreboard.vo.Team;
import com.mholodniuk.scoreboard.vo.util.Pair;

import java.time.LocalDateTime;
import java.util.*;

import static com.mholodniuk.scoreboard.util.NullSafetyUtils.requireNonNull;
import static com.mholodniuk.scoreboard.util.StreamUtils.findFirstMatchingOrNull;

public class Scoreboard {
    private final List<Pair<LocalDateTime, Game>> activeGames = new ArrayList<>();
    private final Set<Team> activeTeams = new HashSet<>();

    public Game startGame(Team homeTeam, Team awayTeam) {
        requireNonNull(homeTeam, awayTeam);
        if (Objects.equals(homeTeam, awayTeam)) {
            throw new IllegalArgumentException("You cannot start a game with the same team");
        }
        if (activeTeams.contains(homeTeam) || activeTeams.contains(awayTeam)) {
            throw new IllegalStateException("One or both teams are already in an active game");
        }
        Game game = new Game(homeTeam, awayTeam);
        activeGames.add(Pair.of(LocalDateTime.now(), game));
        activeTeams.addAll(List.of(homeTeam, awayTeam));
        return game;
    }

    public void updateGameScore(Game game, Score score) {
        requireNonNull(game, score);
        for (var activeGame : activeGames()) {
            if (Objects.equals(game, activeGame)) {
                game.updateScore(score);
            }
        }
    }

    public void finishGame(Game game) {
        requireNonNull(game);
        var gameToFinish = findFirstMatchingOrNull(activeGames, (insertionTimeAndGamePair) -> Objects.equals(game, insertionTimeAndGamePair.second()));
        if (activeGames.remove(gameToFinish)) {
            game.teams().forEach(activeTeams::remove);
        }
    }

    public ScoreboardSummary collectSummary() {
        var orderedGames = activeGames.stream().sorted(new GameAndInsertionTimeComparator()).map(Pair::second).toList();
        return ScoreboardSummary.of(orderedGames);
    }

    private List<Game> activeGames() {
        return activeGames.stream().map(Pair::second).toList();
    }
}
