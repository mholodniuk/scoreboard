package com.mholodniuk.scoreboard.vo;


public record Score(int homeScore, int awayScore) {

    public Score {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    public Score() {
        this(0, 0);
    }

    public Score update(int homeScore, int awayScore) {
        return new Score(homeScore, awayScore);
    }
}
