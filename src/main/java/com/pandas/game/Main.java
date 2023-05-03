package com.pandas.game;

import com.pandas.game.domain.Difficulty;
import com.pandas.game.screen.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int SCREEN_WIDTH = 1200;
    private static final int SCREEN_HEIGHT = 800;

    private Stage mainWindow;

    @Override
    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        mainWindow.setTitle("Tower Defense Game");
        initFirstScreen();
    }

    private void initFirstScreen() {

        StartScreen screen = new StartScreen(SCREEN_WIDTH, SCREEN_HEIGHT);

        Button quitButton = screen.getQuitButton();
        quitButton.setOnAction(e -> mainWindow.close());

        Button playButton = screen.getPlayButton();
        playButton.setOnAction(e -> initConfigurationScreen());

        Scene scene = screen.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void initConfigurationScreen() {

        ConfigurationScreen screen = new ConfigurationScreen(SCREEN_WIDTH, SCREEN_HEIGHT);

        screen.setStartButtonAction(e -> initGameScreen(screen.getName(),
                screen.getSelectedDifficulty()));

        Scene scene = screen.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void initGameScreen(String name, Difficulty difficulty) {

        GameScreen screen = new GameScreen(name, difficulty,
            e -> initGameOverScreen(), e -> initWinScreen(), SCREEN_WIDTH, SCREEN_HEIGHT);
        Scene scene = screen.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }

    private void initGameOverScreen() {

        GameOverScreen screen = new GameOverScreen(SCREEN_WIDTH, SCREEN_HEIGHT, "Game Over! You did well :)");
        Button restartButton = screen.getRestartButton();
        restartButton.setOnAction(e -> initFirstScreen());
        Button quitButton = screen.getQuitButton();
        quitButton.setOnAction(e -> mainWindow.close());
        Scene scene = screen.getScene();
        scene.setFill(Color.GREEN);
        mainWindow.setScene(scene);
        mainWindow.show();

    }

    private void initWinScreen() {
        GameOverScreen screen = new GameOverScreen(SCREEN_WIDTH, SCREEN_HEIGHT, "Congratulations, you won the game!");
        Button restartButton = screen.getRestartButton();
        restartButton.setOnAction(e -> initFirstScreen());
        Button quitButton = screen.getQuitButton();
        quitButton.setOnAction(e -> mainWindow.close());
        Scene scene = screen.getScene();
        scene.setFill(Color.GREEN);
        mainWindow.setScene(scene);
        mainWindow.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
