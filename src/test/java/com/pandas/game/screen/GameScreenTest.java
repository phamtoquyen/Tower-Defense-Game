package com.pandas.game.screen;

import com.pandas.game.constant.TextConstant;
import com.pandas.game.domain.Bullet;
import com.pandas.game.domain.Difficulty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.service.query.NodeQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GameScreenTest extends ApplicationTest {

    private GameScreen gameScreen;

    @Override
    public void start(Stage stage) {

        gameScreen = new GameScreen("Name", Difficulty.EASY, (e) -> { }, (e) -> { }, 1200, 800);

        stage.setScene(gameScreen.getScene());
        stage.show();
    }

    @Test
    public void moneyAndMonumentHealthShouldMatchDifficulty() {
        FxAssert.verifyThat(
                "#moneyLabel",
                LabeledMatchers.hasText(
                        TextConstant.MONEY_LABEL + Difficulty.EASY.getStartingMoney()));
        FxAssert.verifyThat(
                "#monumentHealthLabel",
                LabeledMatchers.hasText(
                        TextConstant.MONUMENT_HEALTH_LABEL + Difficulty.EASY.getMonumentHealth()));
    }

    @Test
    public void buyTowerDoesNotPlaceTower() {
        clickOn("#buyTower1");
        type(KeyCode.ENTER);

        assertFalse(gameScreen.getHasBeenPlaced());
    }

    @Test
    public void canNotPlaceTowerWithoutPayment() {
        drag("#tower1");
        moveTo("#monument");
        assertFalse(gameScreen.getHasBeenPlaced());
    }

    @Test
    public void descriptionMatchesTowerType() {
        clickOn("#tower1");
        NodeQuery q = lookup(".label")
                .lookup(LabeledMatchers.hasText("Description:\nThis is a baby Tower for\n "
                + Difficulty.EASY.getTitle() + " level"));
        Assert.assertNotEquals(Optional.empty(), q.tryQuery());
    }

    @Test(expected = EmptyNodeQueryException.class)
    public void clickOnTowerDoesNotYieldDifferentDescription() {
        clickOn("#tower1");
        NodeQuery q = lookup(".label")
                .lookup(LabeledMatchers.hasText("Description:\nThis is a okay type Tower for\n "
                        + Difficulty.EASY.getTitle() + " level"));
        q.query();
    }

    @Test
    public void noInitialDescription() {
        NodeQuery q1 = lookup(".label")
                .lookup(LabeledMatchers.hasText("Description:\nThis is a baby Tower for\n "
                + Difficulty.EASY.getTitle() + " level"));
        NodeQuery q2 = lookup(".label")
                .lookup(LabeledMatchers.hasText("Description:\nThis is a okay type Tower for\n "
                + Difficulty.EASY.getTitle() + " level"));
        NodeQuery q3 = lookup(".label")
                .lookup(LabeledMatchers.hasText("Description:\nThis is a fancy Tower for\n "
                + Difficulty.EASY.getTitle() + " level"));
        Assert.assertEquals(Optional.empty(), q1.tryQuery());
        Assert.assertEquals(Optional.empty(), q2.tryQuery());
        Assert.assertEquals(Optional.empty(), q3.tryQuery());

    }

    @Test
    public void dragTowerOnMonumentDoesNotPlaceTower() {
        clickOn("#buyTower1");
        type(KeyCode.ENTER);
        drag("#tower1");
        moveTo("#monument");

        assertFalse(gameScreen.getHasBeenPlaced());
    }

    @Test
    public void dragTowerOnPathDoesNotPlaceTower() {
        clickOn("#buyTower1");
        type(KeyCode.ENTER);
        drag("#tower1");
        moveTo("#pathStart");

        assertFalse(gameScreen.getHasBeenPlaced());
    }

    @Test
    public void dragTowerOnValidLocationDoesPlaceTower() {
        clickOn("#buyTower1");
        type(KeyCode.ENTER);
        drag("#tower1");
        moveTo("#towerLocation");
        release(MouseButton.PRIMARY);

        assertTrue(gameScreen.getHasBeenPlaced());
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
        assertTrue(gameScreen.hasGameStarted());

    }

    @Test
    public void ensureGameDoesNotStartUponCreation() {

        assertFalse(gameScreen.hasGameStarted());

    }

    @Test
    public void bulletCannotTravelPastOuterWall() {

        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#startGameButton");
        drag("#tower1");
        dropTo("#3-5");

        boolean hasBulletTravelledPastOuterWall = false;
        for (int i = 0; i < 10; i++) {
            sleep(100);
            for (Bullet b : gameScreen.getBullets()) {
                if (b != null
                        && (b.getX() < 0 || b.getX() > 800)
                        && (b.getY() < 0 || b.getY() > 800)) {
                    hasBulletTravelledPastOuterWall = true;
                    break;
                }
            }
        }

        assertFalse(hasBulletTravelledPastOuterWall);
    }

    @Test
    public void removeBulletWhenReachOuterWall() {

        clickOn("#buyTower1");
        clickOn("OK");
        clickOn("#startGameButton");
        drag("#tower1");
        dropTo("#3-5");

        List<Bullet> oldBullets = List.copyOf(gameScreen.getBullets());

        Awaitility.await().until(() -> !oldBullets.equals(gameScreen.getBullets()));
    }

}
