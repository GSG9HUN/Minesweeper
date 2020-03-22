package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void Exit(){
        System.exit(0);
    }
    @FXML
    private void Options() throws IOException {
       App.setRoot("options");

    }
}
