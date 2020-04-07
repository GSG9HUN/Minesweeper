
module org.examples {
        requires javafx.controls;
        requires javafx.fxml;
        requires org.tinylog.api;

        opens org.example to javafx.fxml;
        exports org.example;
        }