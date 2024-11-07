package com.example.womenshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("women-shop.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 770);
        stage.getIcons().setAll(new Image(getClass().getResource("rose.png").toExternalForm()));
        stage.setTitle("Rosula");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}