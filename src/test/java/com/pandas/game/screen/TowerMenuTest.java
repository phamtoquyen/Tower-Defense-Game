package com.pandas.game.screen;
import com.pandas.game.domain.Difficulty;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.ButtonMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;

public class TowerMenuTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        GameScreen screen = new GameScreen("Example", Difficulty.MEDIUM, (e) -> { }, (e) -> { }, 1200, 800);
        stage.setScene(screen.getScene());
        stage.show();
    }

    @Test
    public void towerPurchaseIsPreventedWhenInsufficientMoney() {
        clickOn("#buyTower3");
        NodeQuery query = lookup(".alert").lookup(NodeMatchers.isVisible());
        Node n = query.query();
        Assert.assertEquals("You know You cannot afford it", ((DialogPane) n).getContentText());
    }


    @Test
    public void towerPurchaseShouldSubtractMoney() {
        clickOn("#buyTower1");
        clickOn(ButtonMatchers.isDefaultButton());
        Node n = lookup("#moneyLabel").query();
        Assert.assertEquals("Money: 500", ((Label) n).getText());
    }


    @Test
    public void canBuyTowerMultipleTimes() {
        clickOn("#buyTower1");
        clickOn(ButtonMatchers.isDefaultButton());
        clickOn("#buyTower1");
        NodeQuery query = lookup(".alert").lookup(NodeMatchers.isVisible());
        Node n = query.query();
        Assert.assertEquals("You know You cannot afford it",
                ((DialogPane) n).getContentText());
    }

}
