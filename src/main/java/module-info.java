
module org.examples {
        requires javafx.controls;
        requires javafx.fxml;
        requires org.tinylog.api;
        requires com.google.gson;
        opens org.example.controller to javafx.fxml;
        exports org.example.controller;
        exports org.example.modell;
        }