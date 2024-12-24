package com.example.emploidutemps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("first_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900, 600);
        stage.setResizable(false);
        stage.setTitle("Emplois du temps UVT");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}