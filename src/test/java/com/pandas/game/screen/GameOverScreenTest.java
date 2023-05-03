package com.pandas.game.screen;


import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.Assert.*;

public class GameOverScreenTest extends ApplicationTest {
    private GameOverScreen screen;
    @Override
    public void start(Stage stage) {
        screen = new GameOverScreen(1200, 800, "");
        stage.setScene(screen.getScene());
        stage.show();
    }
    // Test if the restartButton exits
    @Test
    public void restartButtonNotNull() {
        FxAssert.verifyThat("#restartButton", NodeMatchers.isNotNull());
    }

    // Test if the quitButton exits
    @Test
    public void quitButtonNotNull() {
        FxAssert.verifyThat("#quitButton", NodeMatchers.isNotNull());
    }

    //Test if the restartButton comes back to startScreen
    @Test
    public void checkRestartButton() {
        clickOn("#restartButton");
        FxAssert.verifyThat("#endScreenLabel", NodeMatchers.isNotNull());
    }


}