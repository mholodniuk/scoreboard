package com.mholodniuk.scoreboard.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void test_EmptyScoreShouldInitializeZeros() {
        var score = new Score();
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
        var updatedScore = new Score().update(1, 1);
        assertEquals(1, updatedScore.homeScore(), "initial home score should be updated");
        assertEquals(1, updatedScore.awayScore(), "initial away score should be updated");
    }

    @Test
    void test_ScoreCannotBeUpdatedWithNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> new Score().update(-1, -1), "cannot update scores with negative values");
    }
}
