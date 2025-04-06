package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.Team;

import java.util.List;

public final class Game {
    private final Team homeTeam;
    private final Team awayTeam;
    private Score score;

    private Game(Team homeTeam, Team awayTeam, Score score) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
    }

    Game(Team homeTeam, Team awayTeam) {
        this(homeTeam, awayTeam, Score.initialize());
    }

    void updateScore(Score score) {
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

    public List<Team> teams() {
        return List.of(homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return homeTeam.name() + " " + score.homeScore() + " - " + awayTeam.name() + " " + score.homeScore();
    }
}