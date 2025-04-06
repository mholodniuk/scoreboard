package com.mholodniuk.scoreboard.vo;

import com.mholodniuk.scoreboard.Game;

import java.util.Collection;
import java.util.List;

public record ScoreboardSummary(List<Game> games) {
    public static ScoreboardSummary of(Collection<Game> games) {
        return new ScoreboardSummary(games.stream().toList());
    }
}
