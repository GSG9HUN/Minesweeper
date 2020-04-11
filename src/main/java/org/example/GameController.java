package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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
         new GameView(img,score,timer,nehezseg);
        img.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    GameModell.handlePrimaryClick( mouseEvent.getX(),mouseEvent.getY());
                    break;
                case SECONDARY:
                    GameModell.handleSecondaryClick(mouseEvent.getX(),mouseEvent.getY());
                    break;
            }
        });


        }





    public void restart(MouseEvent mouseEvent) throws IOException {
        GameModell.restart();
    }
}

