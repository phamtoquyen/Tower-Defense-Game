package com.pandas.game.screen;

import com.pandas.game.domain.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.Event;

import java.awt.*;
import java.util.*;
import java.util.List;


public class GameScreen {

    private final int width;
    private final int height;
    private EventHandler<Event> winGameEventHandler;
    private boolean hasBeenPlaced = false;
    private boolean hasGameStarted = false;

    private static final EventType<Event> LOSE_GAME = new EventType<>("Lose Game");
    private static final EventType<Event> WIN_GAME = new EventType<>("Win Game");

    private int money;
    private int monumentHealth;
    private final String name;
    private Difficulty difficulty;
    private ImageView[][] tiles;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Tower> towers;
    private List<List<Enemy>> waves;
    private Point[] path;
    private int blockWidth;
    private int blockHeight;
    private Scene scene;
    private StringProperty monumentStringProperty;
    private StringProperty moneyProperty;
    private EventHandler<Event> loseGameEventHandler;
    private StackPane mapStack;
    private Canvas canvas;
    private Player player;
    private TowerMenu tm;

    public GameScreen(String name, Difficulty difficulty,
                      EventHandler<Event> gameOver, EventHandler<Event> winGame, int width, int height) {

        money = difficulty.getStartingMoney();
        monumentHealth = difficulty.getMonumentHealth();
        this.name = name;
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.loseGameEventHandler = gameOver;
        this.winGameEventHandler = winGame;
        this.mapStack = new StackPane();
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.waves = new ArrayList<>();
    }

    public Scene getScene() {

        Label moneyLabel = new Label("Money: " + money);
        moneyLabel.setId("moneyLabel");
        moneyProperty = new SimpleStringProperty();
        moneyProperty.setValue("Money: " + money);
        moneyLabel.textProperty().bind(moneyProperty);


        Label monumentHealthLabel = new Label("Monument Health: " + monumentHealth);
        monumentStringProperty = new SimpleStringProperty();
        monumentStringProperty.setValue("Monument Health: " + monumentHealth);
        monumentHealthLabel.textProperty().bind(monumentStringProperty);
        monumentHealthLabel.setId("monumentHealthLabel");
        monumentHealthLabel.getStyleClass().add("statusText");


        Rectangle monument = new Rectangle(450, 350, 50, 50);
        monument.setId("monument");
        monument.setFill(Color.GREEN);

        player = new Player(name, monumentHealth, money, difficulty);
        tm = new TowerMenu(player, moneyProperty);

        blockHeight = 100;
        blockWidth = 100;
        path = new Point[]
            {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0),
            new Point(3, 1), new Point(3, 2), new Point(3, 3), new Point(3, 4),
            new Point(2, 4), new Point(1, 4), new Point(1, 3), new Point(1, 2),
            new Point(2, 2), new Point(3, 2), new Point(4, 2), new Point(5, 2),
            new Point(6, 2), new Point(7, 2), new Point(7, 3),
            new Point(7, 4), new Point(7, 5), new Point(8, 5)};
        GridPane mapPane = new GridPane();
        tiles = new ImageView[9][6];

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                ImageView im = new ImageView("file:src/main/resources/Bamboo2.jpg");
                im.setFitWidth(blockHeight);
                im.setFitHeight(blockWidth);
                tiles[i][j] = im;
                im.setId(i + "-" + j);
                mapPane.add(im, i, j);
            }
        }
        for (Point p: path) {
            Image img = new Image("file:src/main/resources/dirt.png");
            tiles[p.x][p.y].setImage(img);
        }

        Point last = path[path.length - 1];
        tiles[last.x][last.y].setImage(new Image("file:src/main/resources/monument.gif"));
        tiles[last.x][last.y].setId("monument");

        tiles[0][0].setId("pathStart");
        tiles[0][5].setId("towerLocation");

        mapPane.setLayoutY(50);
        ImageView im = new ImageView("file:src/main/resources/bamboo1.jpg");
        //im.setFitWidth(800);
        //im.setFitHeight(800);

        mapPane.setBackground(new Background(new BackgroundImage(im.getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));

        mapStack.setAlignment(Pos.TOP_LEFT);
        canvas = new Canvas(blockWidth * tiles.length, blockHeight * tiles[0].length);
        mapStack.getChildren().add(mapPane);
        mapStack.getChildren().add(canvas);
        canvas.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
        canvas.setOnDragDropped(event -> {
            int i = (int)(event.getX()/blockWidth);
            int j = (int)(event.getY()/blockHeight);
            for (Point p : path) {
                if (p.x == i && p.y == j) {
                    event.consume();
                    return;
                }
            }
            event.acceptTransferModes(TransferMode.COPY);
            Image img = event.getDragboard().getImage();
            int towerType = Integer.parseInt(event.getDragboard().getString());
            tiles[i][j].setImage(img);
            towers.add(new Tower(this,i * blockWidth, j * blockHeight, 1));
            player.getInventory()
                    .put(
                            towerType,
                            player.getInventory().get(towerType) - 1);
            hasBeenPlaced = true;
            event.consume();
        });
        canvas.setOnMouseClicked(event -> {
            int i = (int)(event.getX()/blockWidth);
            int j = (int)(event.getY()/blockHeight);
            for (Point p : path) {
                if (p.x == i && p.y == j) {
                    event.consume();
                    return;
                }
            }
            MenuTower mt = new MenuTower(Difficulty.EASY, 4);
            for (Iterator<Tower> towerIterator = towers.iterator(); towerIterator.hasNext();) {
                Tower tower = towerIterator.next();
                if (tower.getX()/blockWidth == i && tower.getY()/blockHeight == j) {
                    if (tower.getLevel() == 2) {
                        return;
                    } else if (!player.afford(mt.getPrice())){
                        new Alert(Alert.AlertType.ERROR, "You know You cannot afford it")
                                .showAndWait()
                                .filter(response -> response == ButtonType.OK);
                    } else {
                        player.buyTower(mt);
                        towerIterator.remove();
                        towers.add(new Tower(this, i * blockWidth, j * blockHeight, 2));
                        tiles[i][j].setImage(new Image("file:src/main/resources/towerUpgrade.gif"));
                        new Alert(Alert.AlertType.CONFIRMATION, "Congratulations\n purchase successful.")
                                .showAndWait()
                                .filter(response -> response == ButtonType.OK);
                    }
                }
            }
        });

        this.waves.add(new ArrayList<Enemy>(Arrays.asList(
                new Enemy(this, path, 0, new Image("file:src/main/resources/en2.jpeg",
                        50,50,false,true), 0),
                new Enemy(this, path, 1, new Image("file:src/main/resources/en2.jpeg",
                        50,50,false,true), 1),
                new Enemy(this, path, 2, new Image("file:src/main/resources/en2.jpeg",
                50,50,false,true), 2))));
        this.waves.add(new ArrayList<Enemy>(Arrays.asList(
                new Enemy(this, path, 0, new Image("file:src/main/resources/en2.jpeg",
                        50,50,false,true), 0),
            new Enemy(this, path, 0, new Image("file:src/main/resources/en2.jpeg",
                50,50,false,true), 1),
            new Enemy(this, path, 1, new Image("file:src/main/resources/en2.jpeg",
                        50,50,false,true), 2))));
        this.waves.add(new ArrayList<Enemy>(Arrays.asList(
                new Enemy(this, path, 0, new Image("file:src/main/resources/enemy3.jpg",
                        50,50,false,true), 3))));
        /*
        for (int i = 0; i < 3; i++) {
            enemies.add(new Enemy(this, path,
                    i, new Image("file:src/main/resources/en2.jpeg",50,50,false,true), i+1));
        }
        */
        Button startGame = new Button("Start Game");
        startGame.setId("startGameButton");
        double timeStarted = System.nanoTime();
        GameLoop g = new GameLoop(this, timeStarted);
        startGame.setOnAction(e -> {
            g.start();
            hasGameStarted = true;
        });
        player = tm.getPlayer();
        //money = player.getMoney();
        Pane towerPane = tm.getPane();
        VBox uiBox = new VBox(moneyLabel, monumentHealthLabel);
        VBox vbox = new VBox(uiBox, mapStack);


        vbox.getStyleClass().add("vbox");
        vbox.getStyleClass().add("back");
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(25));
        HBox hbox = new HBox(vbox, towerPane, startGame);
        hbox.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        scene = new Scene(hbox, width, height);
        scene.setFill(Color.GREEN);
        scene.addEventHandler(GameScreen.LOSE_GAME, loseGameEventHandler);
        scene.addEventHandler(GameScreen.WIN_GAME, winGameEventHandler);
        return scene;
    }
    public boolean getHasBeenPlaced() {
        return hasBeenPlaced;
    }
    public void setHasBeenPlaced(boolean b) {
        hasBeenPlaced = b;
    }

    public List<Enemy> getEnemies() {return enemies;}
    public List<Bullet> getBullets() {return bullets;}
    public List<Tower> getTowers() {return towers;}
    public List<List<Enemy>> getWaves() {return waves;}
    public Point[] getPath() {
        return path;
    }
    public int getBlockWidth() {
        return blockWidth;
    }
    public int getBlockHeight() {
        return blockHeight;
    }
    public double getCanvasHeight() {
        return canvas.getHeight();
    }
    public double getCanvasWidth() {
        return canvas.getWidth();
    }
    public StringProperty getMonumentHealthStringProperty() {
        return monumentStringProperty;
    }

    public int getMonumentHealth() {
        return monumentHealth;
    }
    public void setMonumentHealth(int health) {
        monumentHealth = health;
    }
    public StringProperty getMoneyStringProperty() {
        return moneyProperty;
    }
    public void setLoseGameEventHandler(EventHandler<Event> eventHandler) {
        this.loseGameEventHandler = eventHandler;
        scene.addEventHandler(GameScreen.LOSE_GAME, this.loseGameEventHandler);
    }
    public void setWinGameEventHandler(EventHandler<Event> eventHandler) {
        this.winGameEventHandler = eventHandler;
        scene.addEventHandler(GameScreen.WIN_GAME, this.winGameEventHandler);
    }
    public void fireLoseGame() {
        Event.fireEvent(scene, new Event(GameScreen.LOSE_GAME));
    }
    public void fireWinGame() {Event.fireEvent(scene, new Event(GameScreen.WIN_GAME));}

    public boolean hasGameStarted() {
        return hasGameStarted;
    }
    public StackPane getStack() {
        return mapStack;
    }
    public GraphicsContext getGraphicsContext() {return canvas.getGraphicsContext2D();}
    public Player getPlayer() {
        return player;
    }
}
