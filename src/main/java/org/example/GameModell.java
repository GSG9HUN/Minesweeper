package org.example;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Random;


public class GameModell {

    public static String nehezseg;
    public static Image mine;
    public static Image flag;
    private static int Score = 0;
    private static int counter;

    static {
        mine = new Image(new GameModell().getClass().getResourceAsStream("mine.png"));
        flag = new Image(new GameModell().getClass().getResourceAsStream("flag.png"));
    }

    public String username;
    private static int mx = -1000;
    private static int my = -1000;
    private static boolean[][] bombs;
    private static int[][] neighbours;
    private static boolean[][] flagged;
    private static boolean[][] revealed;
    private static Random rand = new Random();
    public static int timer = 1;
    public static int timercounter = 0;
    public static int stop = 0;
    public static Timeline clock = new Timeline();
    public static int revealedcounter = 1;
    public static boolean revealed1[][];


    public static void howmuchneighbour() {
        Logger.info("Mezők aknásmezőkkel való szomszédainak meghatározása!");
        for (int i = 1; i <= 10; i++)
            for (int j = 1; j <= 10; j++) {
                if (bombs[j][i]) {
                    neighbours[i - 1][j - 1]++;
                    neighbours[i - 1][j]++;
                    neighbours[i - 1][j + 1]++;
                    neighbours[i][j - 1]++;
                    neighbours[i][j + 1]++;
                    neighbours[i + 1][j - 1]++;
                    neighbours[i + 1][j]++;
                    neighbours[i + 1][j + 1]++;
                }

            }
    }

    static int neighbournumber(int x, int y) {
        int i = 0;
        if (x == 1 && y == 10) {
            if (bombs[x][y - 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x + 1][y])
                i++;
        } else if (x == 10 && y == 1) {
            if (bombs[x][y + 1])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x - 1][y])
                i++;
        } else if (x == 1 && y == 1) {
            if (bombs[x][y + 1])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
            if (bombs[x + 1][y])
                i++;
        } else if (x == 10 && y == 10) {
            if (bombs[x][y - 1])
                i++;
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y])
                i++;
        } else if (x == 10 && y != 1) {

            if (bombs[x][y - 1])
                i++;
            if (bombs[x][y + 1])
                i++;
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x - 1][y])
                i++;
        } else if (x != 1 && y == 10) {

            if (bombs[x][y - 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y])
                i++;
            if (bombs[x + 1][y])
                i++;
        } else if (x == 1 && y != 10) {
            if (bombs[x][y - 1])
                i++;
            if (bombs[x][y + 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x + 1][y])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
        } else if (y == 1 && x != 10) {
            if (bombs[x + 1][y])
                i++;
            if (bombs[x - 1][y])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x][y + 1])
                i++;
        } else {
            if (bombs[x - 1][y - 1])
                i++;
            if (bombs[x - 1][y])
                i++;
            if (bombs[x - 1][y + 1])
                i++;
            if (bombs[x][y - 1])
                i++;
            if (bombs[x][y + 1])
                i++;
            if (bombs[x + 1][y - 1])
                i++;
            if (bombs[x + 1][y])
                i++;
            if (bombs[x + 1][y + 1])
                i++;
        }
        return i;
    }

    public static void neighbour(int x, int y, GraphicsContext gc) {

        int szam = neighbournumber(x, y);
        if (szam > 0) {
            switch (szam) {
                case 1:
                    gc.setStroke(Color.BLUE);
                    break;
                case 2:
                    gc.setStroke(Color.GREEN);
                    break;
                case 3:
                    gc.setStroke(Color.RED);
                    break;
                case 4:
                    gc.setStroke(Color.DARKBLUE);
                    break;
                case 5:
                    gc.setStroke(Color.BROWN);
                    break;
                case 6:
                    gc.setStroke(Color.CYAN);
                    break;
                case 7:
                    gc.setStroke(Color.PURPLE);
                    break;
                case 8:
                    gc.setStroke(Color.DARKGRAY);
                    break;

            }
            if (revealed[x - 1][y - 1] == false) {
                gc.fillRect(GameController.spacing + (x - 1) * 50, GameController.spacing + (y - 1) * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * GameController.spacing);
                revealed[x - 1][y - 1] = true;
                revealed1[x][y] = true;
                Logger.info(x + " " + y + " Mező felfordítása!");
                gc.strokeText(String.valueOf(szam), GameController.spacing + (x - 1) * 50 + 20, GameController.spacing + (y - 1) * 50 + 50 + 20);
                Score += 10;
            }
        } else {
            while (szam == 0 && revealed[x - 1][y - 1] == false) {


                gc.setFill(Color.GRAY);
                revealed[x - 1][y - 1] = true;
                revealed1[x][y] = true;
                Logger.info(x + " " + y + " Mező felfordítása!");
                gc.fillRect(GameController.spacing + (x - 1) * 50, GameController.spacing + (y - 1) * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * GameController.spacing);
                Score += 10;

                if (x == 1 && y == 10) {
                    neighbour(x, y - 1, gc);
                    neighbour(x + 1, y - 1, gc);
                    neighbour(x + 1, y, gc);
                    break;
                } else if (x == 10 && y == 1) {
                    neighbour(x, y + 1, gc);
                    neighbour(x - 1, y + 1, gc);
                    neighbour(x - 1, y, gc);
                    break;
                } else if (x == 1 && y == 1) {
                    neighbour(x, y + 1, gc);
                    neighbour(x + 1, y + 1, gc);
                    neighbour(x + 1, y, gc);
                    break;
                } else if (x == 10 && y == 10) {
                    neighbour(x, y - 1, gc);
                    neighbour(x - 1, y - 1, gc);
                    neighbour(x - 1, y, gc);
                    break;

                } else if (x == 10 && y != 1) {
                    neighbour(x, y - 1, gc);
                    neighbour(x, y + 1, gc);
                    neighbour(x - 1, y - 1, gc);
                    neighbour(x - 1, y, gc);
                    neighbour(x - 1, y + 1, gc);
                    break;
                } else if (x != 1 && y == 10) {
                    neighbour(x, y - 1, gc);
                    neighbour(x - 1, y - 1, gc);
                    neighbour(x - 1, y, gc);
                    neighbour(x + 1, y - 1, gc);
                    neighbour(x + 1, y, gc);
                    break;
                } else if (x == 1 && y != 10) {
                    neighbour(x, y - 1, gc);
                    neighbour(x, y + 1, gc);
                    neighbour(x + 1, y - 1, gc);
                    neighbour(x + 1, y, gc);
                    neighbour(x + 1, y + 1, gc);
                    break;
                } else if (y == 1 && x != 10) {
                    neighbour(x + 1, y, gc);
                    neighbour(x - 1, y, gc);
                    neighbour(x + 1, y + 1, gc);
                    neighbour(x - 1, y + 1, gc);
                    neighbour(x, y + 1, gc);
                    break;
                } else if (y > 1 && x < 10 && y < 10 && x > 1) {
                    neighbour(x - 1, y - 1, gc);
                    neighbour(x - 1, y, gc);
                    neighbour(x - 1, y + 1, gc);
                    neighbour(x, y - 1, gc);
                    neighbour(x, y + 1, gc);
                    neighbour(x + 1, y - 1, gc);
                    neighbour(x + 1, y, gc);
                    neighbour(x + 1, y + 1, gc);
                    break;
                } else {
                    break;
                }

            }
        }
    }

    public static void handleSecondaryClick(GraphicsContext gc, MouseEvent mouseEvent) {

        mx = (int) mouseEvent.getX();
        my = (int) mouseEvent.getY();
        if (inboxX() > -1 && inboxY() > -1) {
            if (!flagged[inboxX() - 1][inboxY() - 1] && revealed[inboxX() - 1][inboxY() - 1] == false) {
                gc.drawImage(flag, (GameController.spacing + (inboxX() - 1) * 50) + 2, (GameController.spacing + (inboxY() - 1) * 50 + 50) + 2);
                Logger.info("Flag lehelyetése!");
                flagged[inboxX() - 1][inboxY() - 1] = true;
            } else if (revealed[inboxX() - 1][inboxY() - 1] == false) {
                gc.setFill(Color.BLACK);
                Logger.info("Flag felvétele!");
                gc.fillRect(GameController.spacing + (inboxX() - 1) * 50, GameController.spacing + (inboxY() - 1) * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * GameController.spacing);
                flagged[inboxX() - 1][inboxY() - 1] = false;
            }


        }
    }

    public static void set_to_zero() {
        for (int row = 1; row <= 10; row++)
            for (int collom = 1; collom <= 10; collom++) {
                bombs[row][collom] = false;
                flagged[row][collom] = false;
                neighbours[row][collom] = 0;
                revealed[row][collom] = false;
                revealed1[row][collom] = false;

            }
        Logger.info("Újra kezdés!");
    }

    public static void BombGenerate(String nehezseg) {
        Logger.info("Aknák elhelyezése a pályán!");
        if (nehezseg.equals("könnyű")) {

            bombs = new boolean[11][11];
            neighbours = new int[15][15];
            flagged = new boolean[11][11];
            revealed = new boolean[11][11];
            revealed1 = new boolean[11][11];
            int i = 1;

            for (int row = 1; row <= 10; row++)
                for (int collom = 1; collom <= 10; collom++) {
                    bombs[row][collom] = false;
                    flagged[row][collom] = false;
                    neighbours[row][collom] = 0;
                    revealed[row][collom] = false;
                    revealed1[row][collom] = false;
                }


            while (i <= 10) {
                int random1 = rand.nextInt((10 - 1) + 1) + 1, random2 = rand.nextInt((10 - 1) + 1) + 1;
                if (random1 >= 1 && random1 <= 10 && random2 >= 1 && random2 <= 10) {

                    if (bombs[random1][random2] != true) {
                        bombs[random1][random2] = true;
                        revealed1[random1][random2] = true;
                        i++;
                    }
                }
            }
        }
        if (nehezseg.equals("közepes")) {
            bombs = new boolean[17][17];
            neighbours = new int[17][17];
            flagged = new boolean[17][17];
            int i = 1;
            for (int row = 1; row <= 16; row++)
                for (int collom = 1; collom <= 16; collom++)
                    bombs[row][collom] = false;
            while (i <= 40) {
                int random1 = rand.nextInt((16 - 1) + 1) + 1, random2 = rand.nextInt((16 - 1) + 1) + 1;
                if (random1 >= 1 && random1 <= 16 && random2 >= 1 && random2 <= 16) {
                    if (bombs[random1][random2] != true) {
                        bombs[random1][random2] = true;
                        i++;
                    }
                }
            }
        }
        if (nehezseg.equals("nehéz")) {
            bombs = new boolean[31][17];
            neighbours = new int[31][17];
            flagged = new boolean[31][17];
            int i = 1;
            for (int row = 1; row <= 30; row++)
                for (int collom = 1; collom <= 16; collom++)
                    bombs[row][collom] = false;
            while (i <= 99) {
                int random1 = rand.nextInt((30 - 1) + 1) + 1, random2 = rand.nextInt((16 - 1) + 1) + 1;
                if (random1 >= 1 && random1 <= 30 && random2 >= 1 && random2 <= 16) {
                    if (bombs[random1][random2] != true) {
                        bombs[random1][random2] = true;
                        i++;
                    }
                }
            }
        }
    }

    GameModell() {
    }

    static Alert alert = new Alert(Alert.AlertType.WARNING);
    static Alert alertwin = new Alert(Alert.AlertType.WARNING);

    GameModell(String username, String nehezseg) throws IOException {

        this.username = username;
        GameModell.nehezseg = nehezseg;

        BombGenerate(nehezseg);
        howmuchneighbour();
        counter++;

        alert.setTitle("Felugró ablak!");
        alert.setHeaderText("Vesztettél!");
        alert.setContentText("Egy bombamezőre kattintottál!");
        alertwin.setTitle("Felugró ablak!");
        alertwin.setHeaderText("Győztél!");
        alertwin.setContentText("Gratulálok megtaláltad az összes aknát!");

    }

    public static void restart(GraphicsContext gc, Label timer, Label score) {
        revealedcounter = 1;
        if (counter > 0) {
            set_to_zero();
        }
        gc.setFill(Color.BLACK);
        if (GameModell.nehezseg.equals("könnyű")) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    gc.fillRect(GameController.spacing + i * 50, GameController.spacing + j * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * GameController.spacing);
        }
        BombGenerate(nehezseg);
        howmuchneighbour();
        Score = 0;
        timer.setText("Timer: 0. s");
        score.setText("Score: 0");
        GameModell.timer = 1;
        timercounter = 0;
        stop++;
        if (stop > 0) {
            clock.stop();
            stop = 0;
        }
    }

    public static void handlePrimaryClick(GraphicsContext gc, MouseEvent event, Label score, Label timer) {
        mx = (int) event.getX();
        my = (int) event.getY();
        if (timercounter == 0) {
            clock = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    timer.setText("Timer: " + Integer.toString(GameModell.timer) + ". s");
                    GameModell.timer++;

                }
            }));
            clock.setCycleCount(Timeline.INDEFINITE);
            clock.play();
            Logger.info("Óra elindult!");


        }
        timercounter = 1;
        if (inboxX() > -1 && inboxY() > -1) {
            if (bombs[inboxX()][inboxY()]) {
                gc.setFill(Color.RED);
                for (int i = 1; i <= 10; i++)
                    for (int j = 1; j <= 10; j++)
                        if (bombs[i][j]) {
                            gc.fillRect(GameController.spacing + (i - 1) * 50, GameController.spacing + (j - 1) * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * GameController.spacing);
                            gc.drawImage(mine, (GameController.spacing + (i - 1) * 50) + 2, (GameController.spacing + (j - 1) * 50 + 50) + 2);


                        }
                Logger.info("Aknára klikkelt!");
                alert.showAndWait();
                clock.stop();
                restart(gc, timer, score);
            } else {
                if (!flagged[inboxX() - 1][inboxY() - 1] && revealed[inboxX() - 1][inboxY() - 1] == false) {

                    gc.setFill(Color.GRAY);
                    gc.fillRect(GameController.spacing + (inboxX() - 1) * 50, GameController.spacing + (inboxY() - 1) * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * GameController.spacing);
                    neighbour(inboxX(), inboxY(), gc);
                    score.setText("Score: " + Integer.toString(Score));
                    for (int i = 1; i <= 10; i++)
                        for (int j = 1; j <= 10; j++) {
                            if (revealed1[i][j] == true) {
                                revealedcounter++;

                            }
                        }


                    if (revealedcounter > 100) {
                        alertwin.showAndWait();
                        restart(gc, timer, score);
                    }
                    revealedcounter = 1;
                }
            }
        }
    }


    public static int inboxX() {
        if (nehezseg.equals("könnyű")) {


            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    int p = i * 50 + 50 - GameController.spacing;
                    int k = j * 50 - GameController.spacing + 100;
                    int c = GameController.spacing + j * 50 + 50;
                    if (mx >= GameController.spacing + i * 50 && mx < p && my >= c && my < k)
                        return i + 1;
                }
        } else if (nehezseg.equals("közepes")) {

        } else if (nehezseg.equals("közepes")) {

        }
        return -1;
    }

    public static int inboxY() {
        if (nehezseg.equals("könnyű")) {
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    int p = i * 50 + 50 - GameController.spacing;
                    int k = j * 50 - GameController.spacing + 100;
                    int c = GameController.spacing + j * 50 + 50;
                    if (mx >= GameController.spacing + i * 50 && mx < p && my >= c && my < k)
                        return j + 1;
                }
        } else if (nehezseg.equals("közepes")) {

        } else if (nehezseg.equals("közepes")) {

        }
        return -1;
    }

}

