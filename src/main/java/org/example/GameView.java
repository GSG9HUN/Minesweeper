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
    public static Timeline clock = new Timeline();
    public static Image mine;
    public static Image flag;
    static {
        mine = new Image(new GameView().getClass().getResourceAsStream("mine.png"));
        flag = new Image(new GameView().getClass().getResourceAsStream("flag.png"));
    }
    public static int spacing = 5;
    public static Canvas img = new Canvas();
    public static Label score= new Label();
    public static Label timer= new Label();
    public static GraphicsContext gc ;
    static Alert alert = new Alert(Alert.AlertType.WARNING);
    static Alert alertwin = new Alert(Alert.AlertType.WARNING);
    GameView(Canvas img, Label score, Label timer, String nehezseg){
        this.img=img;
        this.score=score;
        this.timer=timer;
        gc = img.getGraphicsContext2D();
        alert.setTitle("Felugró ablak!");
        alert.setHeaderText("Vesztettél!");
        alert.setContentText("Egy bombamezőre kattintottál!");
        alertwin.setTitle("Felugró ablak!");
        alertwin.setHeaderText("Győztél!");
        alertwin.setContentText("Gratulálok megtaláltad az összes aknát!");

        gc.setFill(Color.BLACK);
        if (GameModell.nehezseg.equals("könnyű")) {
            Logger.info("Pálya kirajzolása könnyű nehézségi szinthez!");
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    gc.fillRect(spacing + i * 50, spacing + j * 50 + 50, 50 - 2 * spacing, 50 - 2 * spacing);

    }
}

public static void DrawFlag(int x, int y){
    gc.drawImage(flag, (GameView.spacing + (x - 1) * 50) + 2, (GameView.spacing + (y - 1) * 50 + 50) + 2);
    Logger.info("Flag lehelyetése!");


}
public static void getFlag(int x, int y){
    gc.setFill(Color.BLACK);
    Logger.info("Flag felvétele!");
    gc.fillRect(GameView.spacing + (x- 1) * 50, GameView.spacing + (y - 1) * 50 + 50, 50 - 2 * GameView.spacing, 50 - 2 * GameView.spacing);
}
    public GameView() {

    }

    public static void setTimer(int timer1) {
        timer.setText("Timer: "+Integer.toString(timer1)+". s");
    }
    public static void setScore(int score1) {
        score.setText("Score: "+Integer.toString(score1));
    }

    public static void DrawMines(int i , int j) {
        gc.setFill(Color.RED);
        gc.fillRect(GameView.spacing + (i - 1) * 50, GameView.spacing + (j - 1) * 50 + 50, 50 - 2 * GameView.spacing, 50 - 2 * GameView.spacing);
        gc.drawImage(mine, (GameView.spacing + (i - 1) * 50) + 2, (GameView.spacing + (j - 1) * 50 + 50) + 2);


    }

    public static void DrawClicked(int inboxX, int inboxY) {
        gc.setFill(Color.GRAY);
        gc.fillRect(GameView.spacing + (inboxX - 1) * 50, GameView.spacing + (inboxY - 1) * 50 + 50, 50 - 2 * GameView.spacing, 50 - 2 * GameView.spacing);
    }

    public static void DrawNeighbour(int szam, int x, int y) {
        gc.strokeText(String.valueOf(szam), GameView.spacing + (x - 1) * 50 + 20, GameView.spacing + (y - 1) * 50 + 50 + 20);
    }

    public static void Drawreveald(int szam, int x, int y) {
        gc.setFill(Color.GRAY);
        gc.fillRect(GameView.spacing + (x - 1) * 50, GameView.spacing + (y - 1) * 50 + 50, 50 - 2 * GameView.spacing, 50 - 2 * GameView.spacing);
    }

    public static void setGCBlue() {
        gc.setStroke(Color.BLUE);
    }

    public static void setGCGreen() {
        gc.setStroke(Color.GREEN);
    }

    public static void setGCRed() {
        gc.setStroke(Color.RED);
    }

    public static void setGCDarkBlue() {
        gc.setStroke(Color.DARKBLUE);
    }

    public static void setGCBrown() {
        gc.setStroke(Color.BROWN);
    }

    public static void setGCCyan() {
        gc.setStroke(Color.CYAN);
    }

    public static void setGCPurple() {
        gc.setStroke(Color.PURPLE);
    }

    public static void setGCDarkGray() {
        gc.setStroke(Color.DARKGRAY);
    }

    public static void WriteScore(int score1) {
        score.setText("Score: " + Integer.toString(score1));
    }

    public static void ShowAlertWin() {
        alertwin.showAndWait();
    }

    public static void Restart() {
        gc.setFill(Color.BLACK);
        if (GameModell.nehezseg.equals("könnyű")) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    gc.fillRect(GameView.spacing + i * 50, GameView.spacing + j * 50 + 50, 50 - 2 * GameView.spacing, 50 - 2 * GameView.spacing);
        }
    }

    public static void ShowAlertLose() {
        GameView.stopClock();
        alert.showAndWait();
        GameModell.restart();
    }

    public static void startClock() {
        clock = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                GameView.setTimer(GameModell.timer);
                GameModell.timer++;

            }
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    public static void stopClock() {
        clock.stop();
    }
}
