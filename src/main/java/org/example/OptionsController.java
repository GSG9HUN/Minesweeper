package org.example;
import java.io.IOException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;


public class OptionsController  {
    public OptionsModell optionsModell;
    @FXML
    private ChoiceBox kivalasztas;
    public OptionsController(){
        this.optionsModell=new OptionsModell();
    }




    @FXML
    public void apply(ActionEvent actionEvent) throws IOException {
       String choiceValue = kivalasztas.getValue().toString();
       optionsModell.setNehezseg(choiceValue);
        App.setRoot("primary");
        }


        }
