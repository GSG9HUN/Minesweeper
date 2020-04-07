package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static int spacing = 5;
    @FXML
    public Canvas img;
    @FXML
    public Label score;
    @FXML
    public Label timer;
    public GraphicsContext gc;
    String nehezseg = new String();
    String username = new String();

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
        img.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    GameModell.handlePrimaryClick(gc, mouseEvent.getX(),mouseEvent.getY(), score, timer);
                    break;
                case SECONDARY:
                    GameModell.handleSecondaryClick(gc, mouseEvent.getX(),mouseEvent.getY());
                    break;
            }
        });

        gc.setFill(Color.BLACK);

        if (GameModell.nehezseg.equals("könnyű")) {
            Logger.info("Pálya kirajzolása könnyű nehézségi szinthez!");
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++)
                    gc.fillRect(spacing + i * 50, spacing + j * 50 + 50, 50 - 2 * GameController.spacing, 50 - 2 * spacing);
        }


    }


    public void restart(MouseEvent mouseEvent) throws IOException {
        GameModell.restart(gc, timer, score);
    }
}

