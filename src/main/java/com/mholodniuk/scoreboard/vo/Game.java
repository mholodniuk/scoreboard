package com.mholodniuk.scoreboard.vo;

import java.time.LocalDateTime;

public record Game(LocalDateTime registeredAt, Team homeTeam, Team awayTeam, Score score) {
    public Game(Team homeTeam, Team awayTeam) {
        this(LocalDateTime.now(), homeTeam, awayTeam, new Score());
    }
}
