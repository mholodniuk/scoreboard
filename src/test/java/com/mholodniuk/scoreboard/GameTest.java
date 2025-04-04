package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.Team;
import com.mholodniuk.scoreboard.vo.util.Pair;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void test_CreateGameInitializesEmptyScore() {
        var team1 = new Team("Team 1");
        var team2 = new Team("Team 2");
        var game = new Game(team1, team2);

        assertEquals(Score.initialize(), game.score());
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

    @Test
    void test_GamesAreSortableByTotalScore() {
        var game1 = createSimpleGame("Team 1", "Team 2", 2, 1);
        var game2 = createSimpleGame("Team 3", "Team 4", 0, 0);
        var insertionTime = LocalDateTime.now();

        var games = Arrays.asList(Pair.of(insertionTime, game1), Pair.of(insertionTime, game2));

        games.sort(new GameAndInsertionTimeComparator());

        var sortedGames = games.stream().map(Pair::second).toList();

        assertEquals(game1, sortedGames.get(0));
        assertEquals(game2, sortedGames.get(1));
    }

    @Test
    void test_GamesAreSortableByInsertionTimeIfTotalScoresAreEqual() {
        var game1 = createSimpleGame("Team 1", "Team 2", 5, 5);
        var game2 = createSimpleGame("Team 3", "Team 4", 10, 0);
        var now = LocalDateTime.now();
        var game1InsertionTime = now.plusSeconds(10);
        var game2InsertionTime = now.plusSeconds(1);

        var games = Arrays.asList(Pair.of(game1InsertionTime, game1), Pair.of(game2InsertionTime, game2));

        games.sort(new GameAndInsertionTimeComparator());

        var sortedGames = games.stream().map(Pair::second).toList();

        assertEquals(game2, sortedGames.get(1));
        assertEquals(game1, sortedGames.get(0));
    }

    private Game createSimpleGame(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        var game = new Game(new Team(homeTeam), new Team(awayTeam));
        game.updateScore(new Score(homeScore, awayScore));
        return game;
    }
}