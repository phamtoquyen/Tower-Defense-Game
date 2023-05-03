package com.pandas.game.screen;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameOverScreen {

    private final int width;
    private final int height;
    private String message;
    private static Button restartButton;
    private static Button quitButton;

    public GameOverScreen(int width, int height, String message) {
        this.width = width;
        this.height = height;
        this.message = message;
        restartButton = new Button("Restart the Game");
        quitButton = new Button("Quit");
    }

    public Scene getScene() {

        Label endScreenLabel = new Label(message);
        endScreenLabel.setId("endScreenLabel");
        endScreenLabel.getStyleClass().add("statusText");

        restartButton.getStyleClass().add("buttons");
        restartButton.setId("restartButton");
        quitButton.getStyleClass().add("buttons");
        quitButton.setId("quitButton");

        VBox vbox = new VBox(endScreenLabel, restartButton, quitButton);
        vbox.getStyleClass().add("vbox");
        vbox.getStyleClass().add("back");
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, width, height);
        return scene;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
