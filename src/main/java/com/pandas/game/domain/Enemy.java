package com.pandas.game.domain;

import com.pandas.game.screen.GameScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.*;
import java.util.Iterator;

public class Enemy {
    private GameScreen screen;
    private GraphicsContext gc;
    private double x;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double y;
    private double vx;
    private double vy;
    private double speed;
    private double health;
    private double h;
    private double w;
    private double bh;
    private double bw;

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    private int hits;
    private int deaths;
    private int type;

    public int getHealthMax() {
        return healthMax;
    }

    private int healthMax;
    private Point[] path;

    public int getStartingIndex() {
        return startingIndex;
    }

    private int startingIndex;

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    private int nextIndex;
    private Image img;
    private Enemy() {
    }
    public Enemy(GameScreen screen, Point[] path, int startingIndex, Image img, int type) {
        this.speed = 10;
        this.img = img;
        this.screen = screen;
        this.gc = screen.getGraphicsContext();
        this.h = img.getHeight();
        this.w = img.getWidth();
        this.type = type;
        this.bh = screen.getBlockHeight();
        this.bw = screen.getBlockWidth();
        this.speed = 4.69420;
        this.path = path;
        this.startingIndex = startingIndex;
        resetPosition();
        if (type == 3) {
            this.healthMax = 15;
        } else {
            this.healthMax = 2;
        }
        this.health = healthMax;
    }
    public boolean hasPassed(Point p) {
        boolean isAtX, isAtY;
        if (vx < 0) {
            isAtX = x <= bw * p.x + bw/2 - w/2;
        } else if (vx > 0) {
            isAtX = x >= bw * p.x + bw/2 - w/2;
        } else {
            isAtX = true;
        }
        if (vy < 0) {
            isAtY = y <= bh * p.y + bh/2 - h/2;
        } else if (vy > 0){
            isAtY = y >= bh * p.y + bh/2 - h/2;
        } else {
            isAtY = true;
        }
        return isAtX && isAtY;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getSpeed() {
        return speed;
    }

    public void update(Iterator<Enemy> enemyIterator) {
        if (hasPassed(path[nextIndex])) {
            if (nextIndex + 1 >= path.length) {
                enemyIterator.remove();
                screen.setMonumentHealth(screen.getMonumentHealth() - type * 50);
                screen.getMonumentHealthStringProperty()
                        .setValue("Monument Health: " + screen.getMonumentHealth());
                resetPosition();
                return;
            } else {
                nextIndex++;
                vx = speed * (path[nextIndex].x - path[nextIndex-1].x);
                vy = speed * (path[nextIndex].y - path[nextIndex-1].y);
            }
        } else {
            x += vx;
            y += vy;
        }
        gc.drawImage(img, x, y);
    }

    public void resetPosition() {
        this.x = bw * path[startingIndex].x + bw/2 - w/2;
        this.y = bh * path[startingIndex].y + bh/2 - h/2;
        this.nextIndex = this.startingIndex;
        this.health = healthMax;
    }
    public Image getImg() {
        return img;
    }

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }
    public double getHealth() {
        return health;
    }

    public void setHealth(double newHealth) {
        health = newHealth;
    }

    public int getType() {
        return type;
    }
}
