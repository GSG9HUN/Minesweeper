package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;



public class OptionsModell {



    @FXML
    public static ChoiceBox<String> kivalasztas = new ChoiceBox<>();


    static void setnehezseg(String newnehezseg) {

        kivalasztas.setValue(newnehezseg);
    }

}
