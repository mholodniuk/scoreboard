package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Game;
import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.ScoreBoardSummary;
import com.mholodniuk.scoreboard.vo.Team;

public interface ScoreBoard {
    Game startGame(Team homeTeam, Team awayTeam);
    void updateGameScore(Game game, Score score);
    void finishGame(Game game);
    ScoreBoardSummary collectSummary();
}
