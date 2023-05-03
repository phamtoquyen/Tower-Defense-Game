package com.pandas.game.screen;

import com.pandas.game.domain.Bullet;
import com.pandas.game.domain.Enemy;
import com.pandas.game.domain.Tower;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;


public class GameLoop extends AnimationTimer {

    private final double timeStarted;
    private final GameScreen screen;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<List<Enemy>> waves;
    private GraphicsContext gc;
    private boolean isOn;
    private double waveDelay;
    private int waveLastSpawned;
    private double lastTimeSpawned;
    private Bullet[] bullet1;



    public GameLoop(GameScreen screen, double timeStarted) {
        this.screen = screen;
        this.timeStarted = timeStarted;
        this.isOn = false;
        enemies = screen.getEnemies();
        bullets = screen.getBullets();
        waves = screen.getWaves();
        this.waveDelay = 5;
        this.lastTimeSpawned = -(waveDelay + 1);
        this.waveLastSpawned = waves.size() - 1;
        gc = screen.getGraphicsContext();
    }

    @Override
    public void start() {
        super.start();
        isOn = true;
    }

    @Override
    public void stop() {
        super.stop();
        isOn = false;
    }

    @Override
    public void handle(long now) {
        double t = (now - timeStarted) / 1000000000.0;
        /*
        Point[] path = screen.getPath();
        Point last = path[path.length - 1];
        screen.setMoney(screen.getPlayer().getMoney());
        screen.getMoneyStringProperty()
                .setValue("Money: " + screen.getMoney());
         */
        boolean hasWonGame = false;
        if (t - lastTimeSpawned >= waveDelay) {
            enemies.addAll(waves.get((waveLastSpawned + 1) % waves.size()));
            lastTimeSpawned = t;
            waveLastSpawned++;
        }
        if (screen.getMonumentHealth() <= 0) {
            screen.fireLoseGame();
            this.stop();
        }
        Enemy e_iter = null;
        Bullet b_iter = null;
        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
            e_iter = enemyIterator.next();
            boolean enemyRemoved = false;
            for (Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext();) {
                b_iter = bulletIterator.next();
                if (detectCollision(e_iter, b_iter)) {

                    e_iter.setHits(e_iter.getHits() + 1);

                    bulletIterator.remove();
                    if (!enemyRemoved && e_iter.getHealth() <= 1) {
                        enemyIterator.remove();
                        if (e_iter.getType() == 3) {
                            hasWonGame = true;
                        }
                        e_iter.resetPosition();
                        enemyRemoved = true;
                        screen.getPlayer().earnMoney(20);
                        screen.getMoneyStringProperty()
                                .setValue("Money: " + screen.getPlayer().getMoney());
                        e_iter.setDeaths(e_iter.getDeaths() + 1);
                    } else if (!enemyRemoved) {
                        e_iter.setHealth(e_iter.getHealth() - 1);
                    }
                }
            }
        }
        gc.clearRect(0, 0, screen.getCanvasWidth(), screen.getCanvasHeight());
        for (Tower tr: screen.getTowers()) {
            tr.fire(t);
        }
        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();) {
            e_iter = enemyIterator.next();
            e_iter.update(enemyIterator);
        }
        for (Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext();) {
            b_iter = bulletIterator.next();
            b_iter.update(bulletIterator);
        }
        if (hasWonGame) {
            screen.fireWinGame();
            this.stop();
        }
    }

    public boolean isOn() {
        return isOn;
    }
    public static boolean detectCollision(Enemy e, Bullet b) {
        return (e.getX() < b.getX() + b.getW() &&
            e.getX() + e.getW() > b.getX() &&
            e.getY() < b.getY() + b.getH() &&
            e.getH() + e.getY() > b.getY());
    }
}
