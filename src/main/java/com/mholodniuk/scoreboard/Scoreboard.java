package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.ScoreboardSummary;
import com.mholodniuk.scoreboard.vo.Team;

import java.util.*;

import static com.mholodniuk.scoreboard.util.NullSafetyUtils.requireNonNull;

public class Scoreboard {
    private final List<Game> activeGames = new ArrayList<>();
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
        activeGames.add(game);
        activeTeams.addAll(List.of(homeTeam, awayTeam));
        return game;
    }

    public void updateGameScore(Game game, Score score) {

    }

    public void finishGame(Game game) {

    }

    public ScoreboardSummary collectSummary() {
        return ScoreboardSummary.of(activeGames);
    }
}
