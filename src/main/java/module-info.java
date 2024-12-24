module com.example.emploidutemps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.emploidutemps.controllers;
    exports com.example.emploidutemps.models;
    opens com.example.emploidutemps to javafx.fxml;
    opens com.example.emploidutemps.controllers to javafx.fxml;
    exports com.example.emploidutemps;
}

