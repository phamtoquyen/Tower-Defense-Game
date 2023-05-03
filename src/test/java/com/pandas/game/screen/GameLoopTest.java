package com.pandas.game.screen;

import com.pandas.game.domain.Bullet;
import com.pandas.game.domain.Difficulty;
import com.pandas.game.domain.Enemy;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class GameLoopTest extends ApplicationTest {
    private GameScreen gameScreen;

    @Override
    public void start(Stage stage) {

        gameScreen = new GameScreen("Name", Difficulty.EASY, (e) -> { }, (e) -> { }, 1200, 800);

        stage.setScene(gameScreen.getScene());
        stage.show();
    }

    @Test
    public void loopChangesEnemyPosition() {
        GameLoop gameLoop = new GameLoop(gameScreen, System.nanoTime());
        gameLoop.start();

        List<Double> originalPositionHash =
                gameScreen.getEnemies().stream().map(e -> e.getX() * e.getY()).collect(Collectors.toList());

        Awaitility.await()
                .until(() -> !originalPositionHash.
                    equals(gameScreen.getEnemies().stream().map(e -> e.getX() * e.getY()).collect(Collectors.toList())));
    }

    @Test
    public void loopDecreasesMonumentHealthWhenEnemyOnMonument() {

        GameLoop gameLoop = new GameLoop(gameScreen, System.nanoTime());
        gameLoop.start();

        int originalMonumentHealth = gameScreen.getMonumentHealth();

        Awaitility.await()
                .until(() -> gameScreen.getMonumentHealth() < originalMonumentHealth);
    }

    @Test
    public void loopEndsWhenMonumentHealthIsZero() {

        GameLoop gameLoop = new GameLoop(gameScreen, System.nanoTime());
        gameLoop.start();

        assertTrue(gameLoop.isOn());

        gameScreen.setMonumentHealth(0);

        Awaitility.await().until(() -> !gameLoop.isOn());

    }

    @Test
    public void loopEndsWhenMonumentHealthIsBelowZero() {

        GameLoop gameLoop = new GameLoop(gameScreen, System.nanoTime());
        gameLoop.start();

        assertTrue(gameLoop.isOn());

        gameScreen.setMonumentHealth(-100);

        Awaitility.await().until(() -> !gameLoop.isOn());

    }

    @Test
    public void bossSpawns() {
        clickOn("#startGameButton");

        Awaitility.await().atMost(Duration.ONE_MINUTE).until(() -> gameScreen.getEnemies().stream().anyMatch(e -> e.getType() == 3));
    }

    @Test
    public void enemy0Spawns() {
        clickOn("#startGameButton");

        Awaitility.await().until(() -> gameScreen.getEnemies().stream().anyMatch(e -> e.getType() == 0));
    }

    @Test
    public void enemy1Spawns() {
        clickOn("#startGameButton");

        Awaitility.await().until(() -> gameScreen.getEnemies().stream().anyMatch(e -> e.getType() == 1));
    }

    @Test
    public void enemy2Spawns() {
        clickOn("#startGameButton");

        Awaitility.await().until(() -> gameScreen.getEnemies().stream().anyMatch(e -> e.getType() == 2));
    }

    @Test
    public void detectCollisionCorrectlyDetectsCollision() {
        Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);
        Bullet b = new Bullet(gameScreen, new Image("file:src/main/resources/bullet.png"), 0, 0, e);

        assertTrue(GameLoop.detectCollision(e, b));
    }

    @Test
    public void detectCollisionCorrectlyDetectsNoCollision() {
        Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);
        Bullet b = new Bullet(gameScreen, new Image("file:src/main/resources/bullet.png"), 100, 100, e);

        assertTrue(GameLoop.detectCollision(e, b));
    }
}
