package com.pandas.game.screen;

import com.pandas.game.domain.Difficulty;
import com.pandas.game.domain.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

  @Test
  public void affordCorrectlyDeterminesThatPlayerCanAfford() {

    Player p = new Player("rs", 100, 100, Difficulty.EASY);

    assertTrue(p.afford(50));
  }

  @Test
  public void affordCorrectlyDeterminesThatPlayerCannotAfford() {

    Player p = new Player("rs", 100, 100, Difficulty.EASY);

    assertFalse(p.afford(200));
  }
}
