package com.pandas.game.screen;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartScreen {

    private final int width;
    private final int height;
    private static Button playButton;
    private static Button quitButton;

    public StartScreen(int width, int height) {

        this.width = width;
        this.height = height;

        playButton = new Button("Play");
        quitButton = new Button("Quit");
    }

    public Scene getScene() {

        Label startScreenLabel = new Label("Tower Defense Game");
        startScreenLabel.setId("startScreenLabel");
        startScreenLabel.getStyleClass().add("statusText");

        playButton.getStyleClass().add("buttons");
        playButton.setId("playButton");
        quitButton.getStyleClass().add("buttons");
        quitButton.setId("quitButton");

        VBox vbox = new VBox(startScreenLabel, playButton, quitButton);
        vbox.getStyleClass().add("vbox");
        vbox.getStyleClass().add("back");
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, width, height);
        scene.getStylesheets().add("file:src/main/resources/StartScreen.css");
        return scene;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getPlayButton() {
        return playButton;
    }
}
