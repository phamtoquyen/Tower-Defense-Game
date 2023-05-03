package com.pandas.game.screen;

import com.pandas.game.domain.Difficulty;
import com.pandas.game.domain.Enemy;
import com.pandas.game.domain.Tower;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TowerTest extends ApplicationTest {

  private GameScreen gameScreen;

  @Override
  public void start(Stage stage) {

    gameScreen = new GameScreen("Name", Difficulty.EASY, (e) -> { }, (e) -> { }, 1200, 800);

    stage.setScene(gameScreen.getScene());
    stage.show();
  }

  @Test
  public void distanceReturnsCorrectTrivialXDistance() {

    Tower t = new Tower(gameScreen, 10, 0, 0);
    Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);

    assertEquals(10, t.distance(e), 0.0);
  }

  @Test
  public void distanceReturnsCorrectTrivialYDistance() {

    Tower t = new Tower(gameScreen, 0, 10, 0);
    Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);

    assertEquals(10, t.distance(e), 0.0);
  }

  @Test
  public void distanceReturnsCorrectDistanceWithDecimals() {

    Tower t = new Tower(gameScreen, 200, 200, 0);
    Enemy e = new Enemy(gameScreen, new Point[] {new Point(0, 0)}, 0, new Image("file:src/main/resources/en2.jpeg"), 0);

    assertEquals(282.8427, t.distance(e), 1.0);
  }
}
