package com.mholodniuk.scoreboard;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ScoreboardTest {

    @Nested
    class CreateGameTest {
        @Test
        void test_StartGameShouldAddNewValidGame() {

        }

        @Test
        void test_StartGameShouldNotAllowDuplicateGames() {

        }

        @Test
        void test_StartGameShouldThrowWhenTeamsAreNull() {

        }

        @Test
        void test_StartGameShouldNotAllowSameTeamForHomeAndAway() {

        }
    }

    @Nested
    class UpdateGameTest {
        void test_UpdateGameScoreShouldUpdateSuccessfullyForTrackedGame() {

        }

        void test_UpdateGameScoreShouldFailForUntrackedGame() {

        }

        void test_UpdateGameScoreShouldRejectNegativeValues() {

        }

        void test_UpdateGameScoreShouldAllowSameScoreUpdate() {

        }
    }

    @Nested
    class FinishGameTest {
        void test_FinishGameShouldRemoveTrackedGame() {

        }

        void test_FinishGameShouldIgnoreUntrackedGame() {

        }

        void test_FinishGameShouldBeIdempotent() {

        }
    }
}