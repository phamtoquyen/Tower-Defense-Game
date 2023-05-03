package com.pandas.game.screen;

import com.pandas.game.domain.Bullet;
import com.pandas.game.domain.Difficulty;
import com.pandas.game.domain.Enemy;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.NodeQuery;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
//import static org.junit.Assert.assertTrue;

public class GameScreenCombatTest extends ApplicationTest {
    private GameScreen gameScreen;

    @Override
    public void start(Stage stage) {

        gameScreen = new GameScreen("Name", Difficulty.EASY, (e) -> { }, (e) -> { }, 1200, 800);

        stage.setScene(gameScreen.getScene());
        stage.show();
    }

    @Test
    public void ensureGameScreenHasStartGameButton() {

        FxAssert.verifyThat(
                "#startGameButton",
                NodeMatchers.isNotNull());
    }

    @Test
    public void ensureStartButtonStartsGame() {

        clickOn("#startGameButton");
        assertFalse(!gameScreen.hasGameStarted());

    }

    @Test
    public void ensureGameDoesNotStartUponCreation() {

        assertFalse(gameScreen.hasGameStarted());

    }

    //Below are tests new to M5:

    @Test
    public void moneyGainWhenEnemyHit() {
        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#startGameButton");
        drag("#tower1");
        dropTo("#3-5");

        Awaitility.await().until(() -> gameScreen.getPlayer().getMoney() > 575);
    }

    @Test
    public void noMoneyGainWhenEnemyNotHit() {
        clickOn("#startGameButton");

        sleep(10000);

        assertEquals(1000, gameScreen.getPlayer().getMoney());
    }

    @Test
    public void removeEnemyWhenEnemyHit() {
        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#startGameButton");
        drag("#tower1");
        dropTo("#3-5");

        Awaitility.await().until(() -> gameScreen.getEnemies().size() > 0);
        Awaitility.await().until(() -> gameScreen.getEnemies().size() == 0);
    }

    @Test
    public void removeBulletWhenEnemyHit() {

        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#startGameButton");
        drag("#tower1");
        dropTo("#3-5");

        Awaitility.await()
                .until(
                        () ->
                                gameScreen.getEnemies().stream()
                                    .reduce(0, (total, enemy) -> total + enemy.getHits(), Integer::sum) >
                                    gameScreen.getBullets().size());
    }

    @Test
    public void ensureTowerFiresBullet() {
        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#startGameButton");
        drag("#tower1");
        dropTo("#3-5");

        Awaitility.await().until(() -> gameScreen.getBullets().size() > 0);
    }

    @Test
    public void canBuyTowerFromEnemyMoney() {
        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#buyTower1");
        clickOn("OK");
        drag("#tower1");
        dropTo("#3-5");
        drag("#tower1");
        dropTo("#0-2");
        clickOn("#startGameButton");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> gameScreen.getPlayer().getMoney() >= 425);
        clickOn("#buyTower1");
        NodeQuery query = lookup(".alert").lookup(NodeMatchers.isVisible());
        Node n = query.query();
        Assert.assertEquals("Congratulations\n purchase successful.",
                ((DialogPane) n).getContentText());
    }

}
