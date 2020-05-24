package org.example.controller;

import javafx.fxml.FXML;
import org.example.App;
import org.tinylog.Logger;
import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void Exit(){
        Logger.info("Kilépés!");
        System.exit(0);
    }
    @FXML
    private void Options() throws IOException {
        Logger.info("Nehézség beállítás!");
       App.setRoot("options");

    }
}
