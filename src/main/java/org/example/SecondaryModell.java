package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;



public class SecondaryModell {

    @FXML
    public static TextField username= new TextField("username");



    public static void setusername(String newusername){

        username = new TextField(newusername);
        System.out.println(username.getText());

    }
}
