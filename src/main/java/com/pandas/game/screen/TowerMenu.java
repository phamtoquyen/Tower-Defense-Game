
package com.pandas.game.screen;


import com.pandas.game.domain.Player;
import com.pandas.game.domain.MenuTower;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class TowerMenu  {

    private StackPane myPane;
    private Label text;
    private VBox last;
    private Label moneyLabel;
    private VBox vbox2;
    private Label monumentHealthLabel;
    private Player player;
    private MenuTower[] tower;
    private StringProperty moneyProperty;

    public TowerMenu(Player player, StringProperty moneyProperty) {
        this.moneyProperty = moneyProperty;
        this.player = player;
        //this.tower = tower;
        tower = new MenuTower[3];
        for (int i = 0; i < 3; i++) {
            tower[i] = new MenuTower(player.getDiff(), i + 1);
        }


    }

    public void initUI() {

        ImageView t1 = new ImageView("file:src/main/resources/Tower1.png");
        t1.setId("tower1");
        tower[0].setImage(t1);
        t1.setOnMouseClicked(e -> description(0)); // Description of the tower.
        t1.setFitHeight(100);
        t1.setFitWidth(100);
        Button b1 = new Button("Red Tower\n $" + tower[0].getPrice());
        System.out.println(tower[0].getPrice());
        b1.setOnAction(e -> {
            purchase(0);
            makeDragable(tower[0], t1);
        });
        b1.setId("buyTower1");

        VBox tower1 = new VBox(t1, b1);

        ImageView t2 =  new ImageView("file:src/main/resources/Tower2.jpg");
        tower[1].setImage(t2);
        t2.setFitHeight(100);
        t2.setFitWidth(100);
        t2.setOnMouseClicked(e -> description(1));
        t2.setId("tower2");
        Button b2 = new Button("Gray Tower\n $" + tower[1].getPrice());
        b2.setOnAction(e -> {
            purchase(1);
            makeDragable(tower[1], t2);
        });
        b2.setId("buyTower2");
        VBox tower2 = new VBox(t2, b2);

        ImageView t3 = new ImageView("file:src/main/resources/Tower3.jpg");
        tower[2].setImage(t3);
        t3.setFitHeight(100);
        t3.setFitWidth(100);
        t3.setOnMouseClicked(e -> description(2));
        t3.setId("tower3");
        Button b3 = new Button("Yellow Tower\n $" + tower[2].getPrice());
        b3.setOnAction(e -> {
            purchase(2);
            makeDragable(tower[2], t3);
        });
        b3.setId("buyTower3");
        VBox tower3 = new VBox(t3, b3);
        //text
        moneyLabel = new Label();
        moneyLabel.setId("moneyLabel");
        monumentHealthLabel = new Label();
        monumentHealthLabel.setId("monumentHealthLabel");

        text =  new Label();
        text.setTextFill(Color.BLACK);
        last = new VBox(text, tower1, tower2, tower3, moneyLabel, monumentHealthLabel);
        last.setSpacing(35);
        myPane = new StackPane();
        ImageView im = new ImageView("file:src/main/resources/toweback.jpg");
        myPane.setBackground(new Background(new BackgroundImage(im.getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        myPane.getChildren().add(last);
        myPane.getStylesheets().add("file:src/main/resources/StartScreen.css");





    }
    public void description(int ind) {
        myPane.getChildren().remove(text);
        text = new Label();
        text.setText(tower[ind].getDescription());
        text.setTextFill(Color.BLACK);
        //text.setAlignment(Pos.BOTTOM_CENTER);
        myPane.getChildren().add(text);
        myPane.setAlignment(text, Pos.TOP_RIGHT);

    }

    public void purchase(int ind) {
        if (player.afford(tower[ind].getPrice())) {
            player.buyTower(tower[ind]);
            updateLabel();
            new Alert(Alert.AlertType.CONFIRMATION, "Congratulations\n purchase successful.")
                    .showAndWait()
                    .filter(response -> response == ButtonType.OK);

        } else {

            new Alert(Alert.AlertType.ERROR, "You know You cannot afford it")
                    .showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }

    }
    public Pane getPane() {
        initUI();
        return myPane;
    }
    public Player getPlayer() {
        return player;
    }
    public void updateLabel() {
        //myPane.getChildren().remove(moneyLabel);
        //moneyLabel =  new Label();
        //moneyLabel.setText("Remaining Money:" + player.getMoney());

        //myPane.getChildren().add(moneyLabel);
        //myPane.setAlignment(moneyLabel, Pos.BOTTOM_CENTER);
        moneyProperty.setValue("Money: " + player.getMoney());


    }

    public void makeDragable(MenuTower tower, ImageView t1) {
        t1.setOnDragDetected(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        /* drag was detected, start a drag-and-drop gesture*/
                        /* allow any transfer mode */
                        if (!player.getInventory().containsKey(tower.getTowerType())
                                || player.getInventory().get(tower.getTowerType()) == 0) {
                            new Alert(
                                            Alert.AlertType.ERROR,
                                            "No towers of this type are in inventory")
                                    .showAndWait()
                                    .filter(response -> response == ButtonType.OK);
                            event.consume();
                            return;
                        }
                        Dragboard db = t1.startDragAndDrop(TransferMode.ANY);

                        /* Put a string on a dragboard */
                        ClipboardContent content = new ClipboardContent();
                        // content.putData(t1);
                        content.putImage(t1.getImage());
                        content.putString(tower.getTowerType() + "");

                        db.setContent(content);
                        event.consume();
                    }
                });
    }



}



