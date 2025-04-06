package com.mholodniuk.scoreboard;

import com.mholodniuk.scoreboard.vo.Score;
import com.mholodniuk.scoreboard.vo.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {

    @Nested
    class CreateGameTest {
        @Test
        void test_StartGameShouldAddNewValidGame() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();

            scoreboard.startGame(team1, team2);

            var scoreboardSummary = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummary.games().size());

            var game = scoreboardSummary.games().getFirst();
            assertEquals(team1, game.homeTeam());
            assertEquals(team2, game.awayTeam());
            assertEquals(new Score(0, 0), game.score());
        }

        @Test
        void test_StartGameShouldNotAllowDuplicateTeamGames() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var team3 = new Team("Team 3");
            var scoreboard = new Scoreboard();

            scoreboard.startGame(team1, team2);

            assertThrows(IllegalStateException.class, () -> scoreboard.startGame(team1, team3));
        }

        @Test
        void test_StartGameShouldThrowWhenEitherTeamIsNullOrEmpty() {
            var team = new Team("Non-null Team");
            var scoreboard = new Scoreboard();

            assertThrows(IllegalArgumentException.class, () -> scoreboard.startGame(null, null));
            assertThrows(IllegalArgumentException.class, () -> scoreboard.startGame(team, null));
            assertThrows(IllegalArgumentException.class, () -> scoreboard.startGame(null, team));
        }

        @Test
        void test_StartGameShouldNotAllowSameTeamForHomeAndAway() {
            var team = new Team("Team");
            var scoreboard = new Scoreboard();

            assertThrows(IllegalArgumentException.class, () -> scoreboard.startGame(team, team));
        }
    }

    @Nested
    class UpdateGameTest {
        @Test
        void test_UpdateGameScoreShouldUpdateSuccessfullyForTrackedGame() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var startedGame = scoreboard.startGame(team1, team2);

            scoreboard.updateGameScore(startedGame, new Score(1, 0));

            var scoreboardSummary = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummary.games().size());

            var game = scoreboardSummary.games().getFirst();
            assertEquals(new Score(1, 0), game.score());
        }

        @Test
        void test_UpdateGameScoreShouldUpdateAllowMultipleUpdatesForTrackedGame() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var startedGame = scoreboard.startGame(team1, team2);

            scoreboard.updateGameScore(startedGame, new Score(1, 0));
            scoreboard.updateGameScore(startedGame, new Score(1, 1));
            scoreboard.updateGameScore(startedGame, new Score(1, 2));

            var scoreboardSummary = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummary.games().size());

            var game = scoreboardSummary.games().getFirst();
            assertEquals(new Score(1, 2), game.score());
        }

        @Test
        void test_UpdateGameScoreShouldDoNothingForUntrackedGame() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var untrackedGame = new Game(team1, team2); // untracked game might come from different scoreboard

            scoreboard.updateGameScore(untrackedGame, new Score(10, 0));

            assertEquals(new Score(0, 0), untrackedGame.score());
        }

        @Test
        void test_UpdateGameScoreShouldFailForNullGame() {
            assertThrows(IllegalArgumentException.class, () -> new Scoreboard().updateGameScore(null, new Score(10, 0)));
        }

        @Test
        void test_UpdateGameScoreShouldFailForNullScore() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var startedGame = scoreboard.startGame(team1, team2);

            assertThrows(IllegalArgumentException.class, () -> scoreboard.updateGameScore(startedGame, null));
        }
    }

    @Nested
    class FinishGameTest {
        @Test
        void test_FinishGameShouldRemoveTrackedGame() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var startedGame = scoreboard.startGame(team1, team2);

            var scoreboardSummaryBeforeFinishing = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummaryBeforeFinishing.games().size());

            scoreboard.finishGame(startedGame);

            var scoreboardSummary = scoreboard.collectSummary();
            assertEquals(0, scoreboardSummary.games().size());
        }

        @Test
        void test_FinishGameShouldIgnoreUntrackedGame() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var team3 = new Team("Team 3");
            var team4 = new Team("Team 4");
            var scoreboard = new Scoreboard();
            scoreboard.startGame(team1, team2);
            var untrackedGame = new Game(team3, team4); // untracked game might come from different scoreboard

            var scoreboardSummaryBeforeFinishing = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummaryBeforeFinishing.games().size());

            scoreboard.finishGame(untrackedGame);

            var scoreboardSummary = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummary.games().size());
        }

        @Test
        void test_FinishGameShouldBeIdempotent() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var startedGame = scoreboard.startGame(team1, team2);

            var scoreboardSummaryBeforeFinishing = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummaryBeforeFinishing.games().size());

            scoreboard.finishGame(startedGame);
            scoreboard.finishGame(startedGame);
            scoreboard.finishGame(startedGame);

            var scoreboardSummary = scoreboard.collectSummary();
            assertEquals(0, scoreboardSummary.games().size());
        }

        @Test
        void test_RestartGameShouldBePossibleAfterFinishing() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var scoreboard = new Scoreboard();
            var startedGame1 = scoreboard.startGame(team1, team2);

            var scoreboardSummary1 = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummary1.games().size());

            scoreboard.finishGame(startedGame1);

            var scoreboardSummary2 = scoreboard.collectSummary();
            assertEquals(0, scoreboardSummary2.games().size());

            scoreboard.startGame(team1, team2);

            var scoreboardSummary3 = scoreboard.collectSummary();
            assertEquals(1, scoreboardSummary3.games().size());
        }

        @Test
        void test_FinishGameShouldNotAllowNullGame() {
            assertThrows(IllegalArgumentException.class, () -> new Scoreboard().finishGame(null));
        }
    }

    @Nested
    class GameSummaryTest {
        @Test
        void test_CollectSummaryShouldReturnEmptyWhenNoGames() {
            var scoreboardSummary = new Scoreboard().collectSummary();
            assertEquals(0, scoreboardSummary.games().size());
        }

        @Test
        void test_CollectSummaryShouldOrderGamesByTotalScoreDescending() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var team3 = new Team("Team 3");
            var team4 = new Team("Team 4");
            var team5 = new Team("Team 5");
            var team6 = new Team("Team 6");

            var scoreboard = new Scoreboard();
            var game1 = scoreboard.startGame(team1, team2);
            var game2 = scoreboard.startGame(team3, team4);
            var game3 = scoreboard.startGame(team5, team6);

            scoreboard.updateGameScore(game1, new Score(1, 2));
            scoreboard.updateGameScore(game2, new Score(2, 0));
            scoreboard.updateGameScore(game3, new Score(3, 4));

            var scoreboardSummary = scoreboard.collectSummary();

            assertEquals(3, scoreboardSummary.games().size());
            assertEquals(game3, scoreboardSummary.games().get(0));
            assertEquals(game1, scoreboardSummary.games().get(1));
            assertEquals(game2, scoreboardSummary.games().get(2));
        }

        @Test
        void test_CollectSummaryShouldUseRecencyForGamesWithSameTotalScore() {
            var team1 = new Team("Team 1");
            var team2 = new Team("Team 2");
            var team3 = new Team("Team 3");
            var team4 = new Team("Team 4");
            var team5 = new Team("Team 5");
            var team6 = new Team("Team 6");

            var scoreboard = new Scoreboard();
            var game1 = scoreboard.startGame(team1, team2);
            var game2 = scoreboard.startGame(team3, team4);
            var game3 = scoreboard.startGame(team5, team6);

            scoreboard.updateGameScore(game1, new Score(2, 2));
            scoreboard.updateGameScore(game2, new Score(2, 0));
            scoreboard.updateGameScore(game3, new Score(0, 4));

            var scoreboardSummary = scoreboard.collectSummary();

            assertEquals(3, scoreboardSummary.games().size());
            assertEquals(game3, scoreboardSummary.games().get(0));
            assertEquals(game1, scoreboardSummary.games().get(1));
            assertEquals(game2, scoreboardSummary.games().get(2));
        }
    }
}