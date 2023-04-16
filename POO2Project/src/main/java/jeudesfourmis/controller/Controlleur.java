package jeudesfourmis.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controlleur extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        BorderPane base = new BorderPane();

        Scene scene = new Scene(base, 600, 500);

        primaryStage.setTitle("Jeux des Fourmis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}