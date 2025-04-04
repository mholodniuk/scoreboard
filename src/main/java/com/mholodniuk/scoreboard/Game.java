package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.Team;
import com.mholodniuk.scoreboard.vo.util.Pair;

import java.time.LocalDateTime;
import java.util.Comparator;

public final class Game {
    private final Team homeTeam;
    private final Team awayTeam;
    private Score score;

    private Game(Team homeTeam, Team awayTeam, Score score) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
    }

    public Game(Team homeTeam, Team awayTeam) {
        this(homeTeam, awayTeam, Score.initialize());
    }

    public void updateScore(Score score) {
        this.score = score;
    }

    public Score score() {
        return score;
    }

    public Team homeTeam() {
        return homeTeam;
    }

    public Team awayTeam() {
        return awayTeam;
    }

    @Override
    public String toString() {
        return homeTeam.name() + " " + score.homeScore() + " - " + awayTeam.name() + " " + score.homeScore();
    }
}

class GameAndInsertionTimeComparator implements Comparator<Pair<LocalDateTime, Game>> {
    @Override
    public int compare(Pair<LocalDateTime, Game> game1, Pair<LocalDateTime, Game> game2) {
        int scoreComparison = Integer.compare(game2.second().score().total(), game1.second().score().total());

        if (scoreComparison != 0) {
            return scoreComparison;
        }

        return game2.first().compareTo(game1.first());
    }
}