package com.pandas.game.domain;

public enum Difficulty {
    EASY("Baby's First Steps", 1000, 1000),
    MEDIUM("Average Joe", 500, 500),
    HARD("Sisyphean", 500, 250);

    private final String title;
    private final int startingMoney;
    private final int monumentHealth;

    Difficulty(String title, int startingMoney, int monumentHealth) {
        this.title = title;
        this.startingMoney = startingMoney;
        this.monumentHealth = monumentHealth;
    }

    public String getTitle() {
        return title;
    }

    public int getStartingMoney() {
        return startingMoney;
    }

    public int getMonumentHealth() {
        return monumentHealth;
    }

    public static Difficulty fromTitle(String title) {

        for (Difficulty difficulty : Difficulty.values()) {
            if (title.equals(difficulty.title)) {
                return difficulty;
            }
        }

        throw new IllegalArgumentException("Invalid difficulty title");
    }
}
