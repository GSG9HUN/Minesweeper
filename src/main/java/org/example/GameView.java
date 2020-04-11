package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.tinylog.Logger;


public class GameView {
    public  Timeline clock = new Timeline();
    public static Image mine;
    public static Image flag;


    static {
        mine = new Image(new GameView().getClass().getResourceAsStream("mine.png"));
        flag = new Image(new GameView().getClass().getResourceAsStream("flag.png"));
    }

    public  int spacing = 5;
    public  Canvas img = new Canvas();
    public Label score = new Label();
    public  Label timer = new Label();
    public GraphicsContext gc;
    Alert alert = new Alert(Alert.AlertType.WARNING);
    Alert alertwin = new Alert(Alert.AlertType.WARNING);
    public String nehezseg= new String();
    private GameView gameView;
    private  GameModell game;
    private GameController controller;

    GameView(GameModell gamecome,GameController controllercome ,Canvas img, Label score, Label timer, String nehezseg) {
        this.img = img;
        this.score = score;
        this.timer = timer;
        controller=controllercome;
        gameView=this;
        this.nehezseg=nehezseg;
        game=gamecome;
        gc = img.getGraphicsContext2D();
        alert.setTitle("Felugró ablak!");
        alert.setHeaderText("Vesztettél!");
        alert.setContentText("Egy bombamezőre kattintottál!");
        alertwin.setTitle("Felugró ablak!");
        alertwin.setHeaderText("Győztél!");
        alertwin.setContentText("Gratulálok megtaláltad az összes aknát!");

        gc.setFill(Color.BLACK);

        if (nehezseg.equals("könnyű")) {
            Logger.info("Pálya kirajzolása könnyű nehézségi szinthez!");
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    gc.fillRect(spacing + i * 50, spacing + j * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);

        }
    }

    public  void DrawFlag(int x, int y) {
        gc.drawImage(flag, (spacing + (x - 1) * 50) + 2, (spacing + (y - 1) * 50 + 50) + 2);
        Logger.info("Flag lehelyetése!");


    }

    public  void getFlag(int x, int y) {
        gc.setFill(Color.BLACK);
        Logger.info("Flag felvétele!");
        gc.fillRect(spacing + (x - 1) * 50, spacing + (y - 1) * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);
    }

    public GameView() {

    }

    public void setTimer(int timer1) {
        timer.setText("Timer: " + Integer.toString(timer1) + ". s");
    }

    public void setScore(int score1) {
        score.setText("Score: " + Integer.toString(score1));
    }

    public  void DrawMines(int i, int j) {
        gc.setFill(Color.RED);
        gc.fillRect(spacing + (i - 1) * 50, spacing + (j - 1) * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);
        gc.drawImage(mine, (spacing + (i - 1) * 50) + 2, (spacing + (j - 1) * 50 + 50) + 2);


    }

    public void DrawClicked(int inboxX, int inboxY) {
        gc.setFill(Color.GRAY);
        gc.fillRect(spacing + (inboxX - 1) * 50, spacing + (inboxY - 1) * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);
    }

    public  void DrawNeighbour(int szam, int x, int y) {
        gc.strokeText(String.valueOf(szam), spacing + (x - 1) * 50 + 20, spacing + (y - 1) * 50 + 50 + 20);
    }

    public  void Drawreveald(int szam, int x, int y) {
        gc.setFill(Color.GRAY);
        gc.fillRect(spacing + (x - 1) * 50, spacing + (y - 1) * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);
    }

    public  void setGCBlue() {
        gc.setStroke(Color.BLUE);
    }

    public void setGCGreen() {
        gc.setStroke(Color.GREEN);
    }

    public  void setGCRed() {
        gc.setStroke(Color.RED);
    }

    public  void setGCDarkBlue() {
        gc.setStroke(Color.DARKBLUE);
    }

    public  void setGCBrown() {
        gc.setStroke(Color.BROWN);
    }

    public  void setGCCyan() {
        gc.setStroke(Color.CYAN);
    }

    public  void setGCPurple() {
        gc.setStroke(Color.PURPLE);
    }

    public  void setGCDarkGray() {
        gc.setStroke(Color.DARKGRAY);
    }

    public void WriteScore(int score1) {
        score.setText("Score: " + Integer.toString(score1));
    }

    public void ShowAlertWin() {
        alertwin.showAndWait();
    }

    public void Restart() {
        gc.setFill(Color.BLACK);
        if (this.nehezseg.equals("könnyű")) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    gc.fillRect(spacing + i * 50, spacing + j * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);
        }
    }

    public void ShowAlertLose() {
        gameView.stopClock();
        alert.showAndWait();

        controller.restart();
    }

    public  void startClock() {
        clock = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                gameView.setTimer(controller.getTime());
                controller.setTime(1);

            }
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    public  void stopClock() {
        clock.stop();
    }

    public int getSpaceing() {
        return spacing;
    }
}
