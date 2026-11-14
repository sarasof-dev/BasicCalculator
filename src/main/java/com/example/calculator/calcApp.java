package com.example.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class calcApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(calcApp.class.getResource("calc-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 620);
        stage.setTitle("Calculadora!");
        stage.setScene(scene);
        stage.show();
    }
}
