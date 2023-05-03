package com.pandas.game.screen;

import com.pandas.game.domain.Player;

import javafx.scene.control.Button;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;




public class MapScreen {
    private Player player;
    public MapScreen(Player player) {

        this.player = player;
        Button btn = new Button("hbk");
        Pane p = new Pane();
        p.getChildren().add(btn);
        //HashMap<Integer, Integer> towerList = player.getBoughtTower();
        VBox vbox = new VBox();
        /*
        for (int i = 0; i < towerList.size(); i++) {
            if (towerList.get(i) != null) {
                ImageView t = towerList.get(i).getImage();
                vbox.getChildren().add(t);
            }
        }
        */
        p.getChildren().add(vbox);


    }






}




