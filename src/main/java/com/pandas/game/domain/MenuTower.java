package com.pandas.game.domain;

import javafx.scene.image.ImageView;


import static com.pandas.game.domain.Difficulty.EASY;
import static com.pandas.game.domain.Difficulty.MEDIUM;

public class MenuTower {

    private int price;
    private String description;
    private Difficulty difficulty;
    private int towerType;
    private final int priceConst = 200;
    private ImageView image;
    public MenuTower(Difficulty difficulty, int towerType) {
        this.difficulty = difficulty;
        description = "";
        this.towerType = towerType;
        price = 1000;

    }

    public int getPrice() {
        this.setPrice();
        return price;
    }

    public String getDescription() {
        this.setDescription();
        return description;
    }

    public void setPrice() {
        if (difficulty == EASY) {
            price = priceConst * 1 + towerType * 225;
        } else if (difficulty == MEDIUM) {
            price = priceConst * 2 + towerType * 225;
        } else {
            price = priceConst * 3 + towerType * 225;
        }

    }
    public void setDescription() {
        if (towerType == 1) {
            description = "Description:\nThis is a baby Tower for\n "
                    + difficulty.getTitle() + " level";
        } else if (towerType == 2) {
            description = "Description:\nThis is a okay type Tower for\n "
                    + difficulty.getTitle() + " level";
        } else {
            description = "Description:\nThis is a fancy Tower for\n "
                    + difficulty.getTitle() + " level";
        }

    }
    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }
    public int getTowerType() {
        return towerType;
    }
    public void setImage(ImageView image) {
        this.image = image;
    }
    public ImageView getImage() {
        return image;
    }


}
