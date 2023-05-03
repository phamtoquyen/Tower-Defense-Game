package com.pandas.game;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

public class MainTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) {
        Main controller = new Main();
        controller.start(primaryStage);
    }

    @Test
    public void initialScreenIsStartScreen() {

        FxAssert.verifyThat("#startScreenLabel", NodeMatchers.isNotNull());
    }


}