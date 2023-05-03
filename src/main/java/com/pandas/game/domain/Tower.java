package com.pandas.game.domain;

import com.pandas.game.screen.GameScreen;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tower {
    double x;
    double y;
    int towerLevel;
    double range;
    double cooldown;
    double lastTimeFired;
    double bh;
    double bw;
    GameScreen screen;
    public Tower(GameScreen screen, int x, int y, int towerLevel) {
        this.x = x;
        this.y = y;
        this.bh = screen.getBlockHeight();
        this.bw = screen.getBlockWidth();
        this.towerLevel = towerLevel;
        if (towerLevel == 2) {
            this.range = 350;
            this.cooldown = 0.15;
        } else {
            this.range = 200;
            this.cooldown = 0.3;
        }
        this.screen = screen;
    }
    public double distance(Enemy e) {
        return Math.sqrt(Math.pow(x + bw/2 - e.getX() - e.getW()/2, 2)
                + Math.pow(y + bh/2 - e.getY() - e.getH()/2, 2));
    }
    public void fire(double time) {
        if (time - lastTimeFired < cooldown) {
            return;
        }
        List<Enemy> proximityList = new ArrayList<>(screen.getEnemies());
        Collections.sort(proximityList, (e1, e2) -> {return (int) Math.signum(distance(e1) - distance(e2));});
        if (proximityList.size() > 0 && distance(proximityList.get(0)) <= range) {
            Image img = new Image("file:src/main/resources/bullet.png", 20,20,false,false);
            screen.getBullets().add(new Bullet(screen, img,
                    x + bw/2 - img.getWidth()/2, y + bh/2 - img.getHeight()/2, proximityList.get(0)));
            lastTimeFired = time;
        }
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getLevel() {
        return towerLevel;
    }
}
