package com.pandas.game.domain;

import com.pandas.game.screen.GameScreen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.*;
import java.util.Iterator;

public class Bullet {
    private Image image;
    private int speed;
    private GraphicsContext gc;
    private GameScreen screen;
    private double x;
    private double vx;
    private double vy;
    private double y;
    private double w;
    private double h;
    private Enemy target;
    public Bullet(GameScreen screen, Image image, double x, double y, Enemy target) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.vx = -10;
        this.vy = 0;
        this.gc = screen.getGraphicsContext();
        this.screen = screen;
        this.h = image.getHeight();
        this.w = image.getWidth();
        this.target = target;
        speed = 10;

    }
    public Image getImg() {
        return image;
    }
    /*
    public void shoot() {
        if (i == 8 || (i >= 4 && j < 2)) {
            //left
            this.image.setTranslateX(this.image.getTranslateX() - speed);
        } else if ((i >= 4 && j > 2) || j == 5) {
            this.image.setTranslateY(this.image.getTranslateY() - speed);
        } else if (i < 1) {
            //right
            this.image.setTranslateY(this.image.getTranslateY() - speed);
        } else {
            this.image.setTranslateX(this.image.getTranslateX() + speed);
        }
    }
    */
    public void update(Iterator<Bullet> bulletIterator) {
        if (x + w < 0 || x > screen.getCanvasWidth() || y + h < 0 || y > screen.getCanvasHeight()) {
            bulletIterator.remove();
        }
        if (screen.getEnemies().contains(target)) {
            vx = target.getX() + target.getW()/2 - x - w/2;
            vy = target.getY() + target.getH()/2 - y - h/2;
            double length = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
            vx = vx/length * speed;
            vy = vy/length * speed;
        }
        x += vx;
        y += vy;
        gc.drawImage(image, x, y);
    }
    public void setSpeed(int speed) {
        this.speed = speed + 5;
    }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getW() {return w;}
    public double getH() {return h;}

}
