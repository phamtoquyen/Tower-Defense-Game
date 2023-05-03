package com.pandas.game.screen;

import com.pandas.game.domain.Difficulty;
import com.pandas.game.domain.Enemy;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class EnemyTest extends ApplicationTest {

  private GameScreen gameScreen;

  @Override
  public void start(Stage stage) {

    gameScreen = new GameScreen("Name", Difficulty.EASY, (e) -> { }, (e) -> { }, 1200, 800);

    stage.setScene(gameScreen.getScene());
    stage.show();
  }

  @Test
  public void resetPositionShouldResetPosition() {

    Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);

    double startingX = e.getX();
    double startingY = e.getY();

    e.setX(10);
    e.setY(10);

    e.resetPosition();

    assertEquals(startingX, e.getX(), 0.0);
    assertEquals(startingY, e.getY(), 0.0);

  }

  @Test
  public void resetPositionShouldResetHealth() {

    Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);

    e.setHealth(1);

    e.resetPosition();

    assertEquals(e.getHealthMax(), e.getHealth(), 0);
  }

  @Test
  public void resetPositionShouldResetNextIndex() {

    Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);

    e.setNextIndex(1923580239);

    e.resetPosition();

    assertEquals(e.getNextIndex(), e.getStartingIndex());
  }
}
