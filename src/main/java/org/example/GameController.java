package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {


    @FXML
    public Canvas img;
    @FXML
    public Label score;
    @FXML
    public Label timer;
    public GraphicsContext gc;
    String nehezseg = new String();
    String username = new String();
    public static int mx = -1000;
    public static int my = -1000;
    public static int szam = 0;

    public GameController() {


    }

    public GameController(String nehezseg, String username) throws IOException {

        this.nehezseg = nehezseg;
        this.username = username;
        new GameModell(username, nehezseg);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = img.getGraphicsContext2D();
        new GameView(img, score, timer, nehezseg);
        img.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    handlePrimaryClick(mouseEvent.getX(), mouseEvent.getY());
                    break;
                case SECONDARY:
                    handleSecondaryClick(mouseEvent.getX(), mouseEvent.getY());
                    break;
            }
        });


    }

    /**
     * Ez a metódus figyeli a jobb klikket. És ha egy olyan mezőre klikkelt ami nincs felfordítva
     * valamint nincs rajta már zászló akkor lerak egy zászlót. Ha van rajta akkor leveszi azt.
     *
     * @param x koordináta
     * @param y koordináta
     */
    public static void handleSecondaryClick(double x, double y) {
        mx = (int) x;
        my = (int) y;

        if (GameModell.inboxX(mx, my) > -1 && GameModell.inboxY(mx, my) > -1) {
            if (GameModell.getFlagged(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1) == false
                    && GameModell.getRevealed(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1) == false) {
                GameView.DrawFlag(GameModell.inboxX(mx, my), GameModell.inboxY(mx, my));
                GameModell.setFlagged(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1, true);
            } else if (GameModell.getRevealed(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1) == false) {
                GameView.getFlag(GameModell.inboxX(mx, my), GameModell.inboxY(mx, my));
                GameModell.setFlagged(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1, false);
            }


        }
    }


    /**
     * Ez a metódus fordítja fel a mezőket, ha akna van rajta akkor kirajzolja az összes helyre az aknákat és
     * egy üzenetet dob fel, hogy kikaptunk majd ha kilépünk az üzenetből elölről kezdődik az egész.
     * Ha viszont nem lépünk aknára akkor a legvégén egy gratulációs üzenetet dob fel és ha kilépünk
     * belőle akkor szintén előlről kezdődik.
     *
     * @param x mező sorszáma
     * @param y mező oszlopszáma
     */
    public static void handlePrimaryClick(double x, double y) {
        mx = (int) x;
        my = (int) y;

        if (GameModell.getTimecounter() == 0) {

            GameView.startClock();

            Logger.info("Óra elindult!");


        }
        GameModell.setTimecounter(1);
        if (GameModell.inboxX(mx, my) > -1 && GameModell.inboxY(mx, my) > -1) {
            if (GameModell.getBombs(GameModell.inboxX(mx, my), GameModell.inboxY(mx, my))) {

                for (int i = 1; i <= 10; i++)
                    for (int j = 1; j <= 10; j++)
                        if (GameModell.getBombs(i, j)) {
                            GameView.DrawMines(i, j);
                        }
                GameView.ShowAlertLose();
                Logger.info("Aknára klikkelt!");
                GameView.stopClock();
            } else {
                if (GameModell.getFlagged(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1) == false &&
                        GameModell.getRevealed(GameModell.inboxX(mx, my) - 1, GameModell.inboxY(mx, my) - 1) == false) {

                    neighbour(GameModell.inboxX(mx, my), GameModell.inboxY(mx, my));
                    GameView.setScore(GameModell.getScore());

                    for (int i = 1; i <= 10; i++)
                        for (int j = 1; j <= 10; j++) {
                            if (GameModell.getRevealed1(i, j) == true) {
                                GameModell.updateRevealedcounter(1);


                            }
                        }


                    if (GameModell.getREvealedcounter() > 100) {
                        GameView.ShowAlertWin();
                        GameModell.restart();
                    }
                    GameModell.setRevealedcounter(1);
                }
            }
        }
    }

    /**
     * Ez a metódus elsőre legkéri mennyi akna van közvetlenül a mező mellett.
     * Ha ez megvolt és van legalább egy akna mellette akkor a mezőt felfordítja
     * és ráírja hány akna van közvetlenül mellette. Ha a szám 0 akkor rekurzívan
     * minden szomszédos mezőre meghívodik és felfordítja őket amíg nem talál olyan mezőt
     * ami mellett van legalább 1 akna.
     *
     * @param x a mező sorszáma
     * @param y a mező oszlopszáma
     * @return void
     */
    public static void neighbour(int x, int y) {

        szam = GameModell.neighbournumber(x, y);
        if (szam > 0) {
            switch (szam) {
                case 1:
                    GameView.setGCBlue();
                    break;
                case 2:
                    GameView.setGCGreen();
                    break;
                case 3:
                    GameView.setGCRed();
                    break;
                case 4:
                    GameView.setGCDarkBlue();
                    break;
                case 5:
                    GameView.setGCBrown();
                    break;
                case 6:
                    GameView.setGCCyan();
                    break;
                case 7:
                    GameView.setGCPurple();
                    break;
                case 8:
                    GameView.setGCDarkGray();
                    break;

            }
            if (GameModell.getRevealed(x - 1, y - 1) == false) {

                GameView.Drawreveald(szam, x, y);
                GameView.DrawNeighbour(szam, x, y);
                GameModell.setRevealedvalue(x - 1, y - 1, true);
                GameModell.setRevealed1value(x, y, true);
                Logger.info(x + " " + y + " Mező felfordítása!");
                GameModell.updateScorevalue(10);

            }
        } else {
            while (szam == 0 && GameModell.getRevealed(x - 1, y - 1) == false) {

                GameModell.setRevealedvalue(x - 1, y - 1, true);
                GameModell.setRevealed1value(x, y, true);
                Logger.info(x + " " + y + " Mező felfordítása!");
                GameView.DrawClicked(x, y);
                GameModell.updateScorevalue(10);

                if (x == 1 && y == 10) {
                    neighbour(x, y - 1);
                    neighbour(x + 1, y - 1);
                    neighbour(x + 1, y);
                    break;
                } else if (x == 10 && y == 1) {
                    neighbour(x, y + 1);
                    neighbour(x - 1, y + 1);
                    neighbour(x - 1, y);
                    break;
                } else if (x == 1 && y == 1) {
                    neighbour(x, y + 1);
                    neighbour(x + 1, y + 1);
                    neighbour(x + 1, y);
                    break;
                } else if (x == 10 && y == 10) {
                    neighbour(x, y - 1);
                    neighbour(x - 1, y - 1);
                    neighbour(x - 1, y);
                    break;

                } else if (x == 10 && y != 1) {
                    neighbour(x, y - 1);
                    neighbour(x, y + 1);
                    neighbour(x - 1, y - 1);
                    neighbour(x - 1, y);
                    neighbour(x - 1, y + 1);
                    break;
                } else if (x != 1 && y == 10) {
                    neighbour(x, y - 1);
                    neighbour(x - 1, y - 1);
                    neighbour(x - 1, y);
                    neighbour(x + 1, y - 1);
                    neighbour(x + 1, y);
                    break;
                } else if (x == 1 && y != 10) {
                    neighbour(x, y - 1);
                    neighbour(x, y + 1);
                    neighbour(x + 1, y - 1);
                    neighbour(x + 1, y);
                    neighbour(x + 1, y + 1);
                    break;
                } else if (y == 1 && x != 10) {
                    neighbour(x + 1, y);
                    neighbour(x - 1, y);
                    neighbour(x + 1, y + 1);
                    neighbour(x - 1, y + 1);
                    neighbour(x, y + 1);
                    break;
                } else if (y > 1 && x < 10 && y < 10 && x > 1) {
                    neighbour(x - 1, y - 1);
                    neighbour(x - 1, y);
                    neighbour(x - 1, y + 1);
                    neighbour(x, y - 1);
                    neighbour(x, y + 1);
                    neighbour(x + 1, y - 1);
                    neighbour(x + 1, y);
                    neighbour(x + 1, y + 1);
                    break;
                } else {
                    break;
                }

            }
        }
    }


    public void restart(MouseEvent mouseEvent) throws IOException {
        GameModell.restart();
    }
}

