package com.pandas.game.screen;

import com.pandas.game.domain.Difficulty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationScreen {

    private final int width;
    private final int height;

    private final ToggleGroup radioButtons;
    private final TextField nameEntry;
    private final Button startButton;

    private final BorderPane pane;
    private final Scene scene;

    public ConfigurationScreen(int width, int height) {

        this.width = width;
        this.height = height;

        radioButtons = new ToggleGroup();

        List<RadioButton> difficultyOptions = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyOptions.add(new RadioButton(difficulty.getTitle()));
        }


        nameEntry = new TextField();
        nameEntry.setId("nameEntry");
        Label configurationScreenLabel = new Label("Start New Game");
        configurationScreenLabel.getStyleClass().add("statusText");
        configurationScreenLabel.setId("configurationScreenLabel");
        Label namePrompt = new Label("Choose a Name");
        namePrompt.getStyleClass().add("statusText");

        Label difficultyPrompt = new Label("Select Difficulty");
        difficultyPrompt.getStyleClass().add("statusText");

        startButton = new Button("Start");
        startButton.setId("startButton");
        startButton.getStyleClass().add("buttons");

        pane = new BorderPane();
        //pane.getStylesheets().add("file:src/main/resources/StartScreen.css");
        VBox centerBox = new VBox();
        HBox nameBox = new HBox();
        HBox difficultyBox = new HBox();

        pane.setCenter(centerBox);
        pane.setTop(configurationScreenLabel);

        centerBox.getChildren().addAll(nameBox, difficultyBox, startButton);
        nameBox.getChildren().addAll(namePrompt, nameEntry);
        difficultyBox.getChildren().add(difficultyPrompt);
        centerBox.setAlignment(Pos.CENTER);
        nameBox.setAlignment(Pos.CENTER);
        difficultyBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(30);
        difficultyOptions.forEach(
            (option) -> {
                option.setToggleGroup(radioButtons);
                difficultyBox.getChildren().add(option);
                option.setId(option.getText());
            });
        difficultyOptions.get(0).setSelected(true);
        VBox vbox = new VBox(configurationScreenLabel, centerBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("Configback");
        scene = new Scene(vbox, width, height);
        scene.getStylesheets().add("file:src/main/resources/StartScreen.css");
    }

    public Scene getScene() {

        return scene;
    }

    public void setStartButtonAction(EventHandler<ActionEvent> eventHandler) {

        startButton.setOnAction(
            e -> {
                if (validateName()) {

                    eventHandler.handle(e);
                }
            });
    }

    public Difficulty getSelectedDifficulty() {

        return Difficulty.fromTitle(((RadioButton) radioButtons.getSelectedToggle()).getText());
    }

    private boolean validateName() {

        String name = nameEntry.getText();

        if (name == null || name.isEmpty() || name.trim().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Name must have at least one non-whitespace character")
                    .showAndWait()
                    .filter(response -> response == ButtonType.OK);

            return false;
        }

        return true;
    }
    public String getName() {
        return nameEntry.getText();
    }
}
