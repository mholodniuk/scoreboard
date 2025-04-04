package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.Team;


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
}
