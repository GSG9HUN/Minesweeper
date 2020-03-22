package org.example;
import java.io.IOException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;



public class OptionsController  {
    @FXML
    public void apply(ActionEvent actionEvent) throws IOException {
        System.out.println(OptionsModell.kivalasztas.getValue());
        OptionsModell.setnehezseg(OptionsModell.kivalasztas.getValue());

       // App.setRoot("primary");
        }


        }
