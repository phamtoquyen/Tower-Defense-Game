package com.pandas.game.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String name;
    private int healthPoint;
    private int money;
    private Difficulty diff;
    private ArrayList<MenuTower> listTower;
    private HashMap<Integer, Integer> inventory;


    public Player(String name, int healthPoint, int money, Difficulty diff) {
        this.name = name;
        this.healthPoint = healthPoint;
        this.money = money;
        this.diff = diff;
        listTower = new ArrayList<>();
        inventory = new HashMap<Integer, Integer>();
        //Note: may want to change tower to Enum in the future to make this code cleaner
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getMoney() {
        return money;
    }
    public void earnMoney(int points) {
        this.money = this.money + points;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDiff() {
        return diff;
    }

    public void buyTower(MenuTower tower) {
        if (!inventory.containsKey(tower.getTowerType())) {
            inventory.put(tower.getTowerType(), 1);
        } else {
            inventory.put(tower.getTowerType(), inventory.get(tower.getTowerType()) + 1);
        }
        this.money -= tower.getPrice();
    }
    public boolean afford(int itemPrice) {
        if (itemPrice > money) {
            return false;
        }
        return true;
    }
    public boolean towerNotInInventory(MenuTower tower) {
        return !inventory.containsKey(tower.getTowerType())
                || (inventory.get(tower.getTowerType()) == 0);

    }
    public HashMap<Integer, Integer> getInventory() {
        return inventory;
    }


}
