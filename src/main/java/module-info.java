module com.example.emploidutemps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.emploidutemps to javafx.fxml;
    exports com.example.emploidutemps;
}

