package jeudesfourmis.vue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jeudesfourmis.vue.tests.grid.GridEditable;

public class MainVue extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        //GridEditable grid = new GridEditable(true, size, seeds, 10, walls, ants);

        // On crée la partie pour le controle de la simulation.
        ControlPanel controlPanel = new ControlPanel();

        // On crée le bouton pour quitter la simulation.
        Button btnQuit = new Button("Quit");
        btnQuit.setOnAction(event ->
        {
            Platform.exit();
        });
        BorderPane.setAlignment(btnQuit, Pos.BOTTOM_RIGHT);

        BorderPane base = new BorderPane();
        //base.setCenter(grid);
        base.setRight(controlPanel);
        base.setBottom(btnQuit);


        Scene scene = new Scene(base, 800, 600);
        primaryStage.setTitle("La Fourmilière");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
