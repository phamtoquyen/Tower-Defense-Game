package com.pandas.game.screen;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

public class StartScreenTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {

        StartScreen startScreen = new StartScreen(1000, 1000);
        stage.setScene(startScreen.getScene());
        stage.show();
    }

    @Test
    public void playAndQuitButtonsAreNotNull() {
        // Maisa
        FxAssert.verifyThat("#playButton", NodeMatchers.isNotNull());
        FxAssert.verifyThat("#quitButton", NodeMatchers.isNotNull());
    }
}
