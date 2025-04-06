package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void test_CreateGameInitializesEmptyScore() {
        var team1 = new Team("Team 1");
        var team2 = new Team("Team 2");
        var game = new Game(team1, team2);

        assertEquals(new Score(0, 0), game.score());
    }

    @Test
    void test_CreatedGameCanHaveScoreUpdated() {
        var team1 = new Team("Team 1");
        var team2 = new Team("Team 2");
        var game = new Game(team1, team2);
        var newScore = new Score(1, 2);

        game.updateScore(newScore);

        assertEquals(newScore, game.score());
    }
}