package com.mholodniuk.scoreboard.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void test_CannotCreateTeamWithNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Team(null), "Name cannot be null");
        assertThrows(IllegalArgumentException.class, () -> new Team(""), "Name cannot be empty");
    }

    @Test
    void test_ShouldCreateTeamWithValidName() {
        var name = "Sample Team";
        var team = new Team(name);
        assertEquals(name, team.name());
    }
}