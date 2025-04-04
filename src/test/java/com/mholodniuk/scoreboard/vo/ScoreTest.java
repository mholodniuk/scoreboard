package com.mholodniuk.scoreboard.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void test_EmptyScoreShouldInitializeZeros() {
        var score = Score.initialize();
        assertEquals(0, score.homeScore(), "initial home score should be zero");
        assertEquals(0, score.awayScore(), "initial away score should be zero");
    }

    @Test
    void test_NewScoreAllowsOnlyPositiveValues() {
        assertThrows(IllegalArgumentException.class, () -> new Score(-1, -1), "initial scores should be negative");
    }

    @Test
    void test_NewScoreCanBeInitializedWithPositiveValues() {
        var score = new Score(1, 1);
        assertEquals(1, score.homeScore(), "initial home score should be initialized with positive value");
        assertEquals(1, score.awayScore(), "initial away score should be initialized with positive value");
    }

    @Test
    void test_ScoreCanBeUpdatedWithPositiveValues() {
        var updatedScore = Score.initialize().update(1, 1);
        assertEquals(1, updatedScore.homeScore(), "initial home score should be updated");
        assertEquals(1, updatedScore.awayScore(), "initial away score should be updated");
    }

    @Test
    void test_ScoreCannotBeUpdatedWithNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> Score.initialize().update(-1, -1), "cannot update scores with negative values");
    }

    @Test
    void test_ScoreTotalIsCalculatedBasingOnHomeAndAwayScores() {
        var score = Score.initialize().update(10, 6);
        assertEquals(16, score.total(), "score's total is not valid");
    }
}
