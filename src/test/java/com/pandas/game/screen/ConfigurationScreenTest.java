package com.pandas.game.screen;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.NodeQuery;

public class ConfigurationScreenTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        ConfigurationScreen configurationScreen = new ConfigurationScreen(1000, 1000);
        stage.setScene(configurationScreen.getScene());
        stage.show();
    }

    @Test
    public void selectOnlyOneDifficulty() {
        clickOn("Baby's First Steps");
        clickOn("Average Joe");
        NodeQuery query1 = lookup("Baby's First Steps");
        NodeQuery query2 = lookup("Average Joe");
        Node easyButton = query1.query();
        Node mediumButton = query2.query();
        Assert.assertFalse(((RadioButton) easyButton).isSelected());
        Assert.assertTrue(((RadioButton) mediumButton).isSelected());
    }

    @Test
    public void startButtonShouldNotStartGameWhenInvalidName() {

        clickOn("#startButton");
        FxAssert.verifyThat("#configurationScreenLabel", NodeMatchers.isNotNull());
    }
}
