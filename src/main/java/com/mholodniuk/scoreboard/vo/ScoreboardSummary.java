package com.mholodniuk.scoreboard.vo;

import com.mholodniuk.scoreboard.Game;

import java.util.List;

public record ScoreboardSummary(List<Game> games) {
}
