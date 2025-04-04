package com.mholodniuk.scoreboard.vo;

public record Team(String name) {
    public Team {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
    }
}
