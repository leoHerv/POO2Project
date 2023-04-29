package jeudesfourmis.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jeudesfourmis.model.FourmiliereModif;
import jeudesfourmis.vue.board.BoardWithZoomBoard;

public class Controller extends Application
{
    int size = 50;
    @Override
    public void start(Stage primaryStage)
    {
        FourmiliereModif fourmiliere = new FourmiliereModif(size, 10);
        int[][] seeds = fourmiliere.getSeedsArray();
        boolean[][] walls = fourmiliere.getWallsArray();
        int[][] ants = fourmiliere.getAntsArray();

        SimpleBooleanProperty zoomActivationProperty = new SimpleBooleanProperty(false);
        SimpleIntegerProperty nbLapsProperty = new SimpleIntegerProperty(0);

        BoardWithZoomBoard board = new BoardWithZoomBoard(true, size, seeds, 10, walls, ants,
                zoomActivationProperty, nbLapsProperty);

        // On crée la partie pour le controle de la simulation.
        SimpleBooleanProperty gridProperty = board.getDisplayGridProperty();
        SimpleBooleanProperty boardEditable = board.getIsEditableProperty();
        gridProperty.addListener(event ->
            {
                board.allDraw();
            });
        ControlPanel controlPanel = new ControlPanel(zoomActivationProperty, gridProperty, boardEditable,
                fourmiliere, board, nbLapsProperty);

        // On crée le bouton pour quitter la simulation.
        Button btnQuit = new Button("Quit");
        btnQuit.setOnAction(event ->
        {
            Platform.exit();
        });
        BorderPane.setAlignment(btnQuit, Pos.BOTTOM_RIGHT);

        BorderPane base = new BorderPane();
        base.setCenter(board);
        base.setRight(controlPanel);
        base.setBottom(btnQuit);


        Scene scene = new Scene(base, 1200, 800);
        primaryStage.setTitle("La Fourmilière");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
