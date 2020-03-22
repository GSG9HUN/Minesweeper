package org.example;

import java.io.IOException;

import javafx.fxml.FXML;


public class SecondaryController {


    @FXML
    public void startgame() throws IOException {

        System.out.println(SecondaryModell.username.getText());

        SecondaryModell.setusername(SecondaryModell.username.getText());
        App.setRoot("secondary");

}
}