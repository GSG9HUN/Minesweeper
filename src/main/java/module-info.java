
module org.examples {
        requires javafx.controls;
        requires javafx.fxml;
        requires org.tinylog.api;
        requires com.google.gson;
        opens org.example to javafx.fxml;
        opens org.example.controller to javafx.fxml;
        exports org.example.controller;
        exports org.example.modell;
        exports org.example.view;
        exports org.example;
        }