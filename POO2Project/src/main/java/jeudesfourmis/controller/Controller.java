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
        int[][] seeds = new int[size][size];
        for(int i = 0; i < size; i++)
        {
            int[] seed = new int[size];
            for(int j = 0; j < size; j++)
            {
                if(j == 1)
                {
                    seed[j] = 5;
                }
                else
                {
                    seed[j] = 0;
                }
            }
            seeds[i] = seed;
        }

        boolean[][] walls = new boolean[size][size];
        for(int i = 0; i < size; i++)
        {
            boolean[] wall = new boolean[size];
            for(int j = 0; j < size; j++)
            {
                if(j == 0)
                {
                    wall[j] = true;
                }
                else
                {
                    wall[j] = false;
                }
            }
            walls[i] = wall;
        }

        int[][] ants = new int[size][size];
        for(int i = 0; i < size; i++)
        {
            int[] ant = new int[size];
            for(int j = 0; j < size; j++)
            {
                if(j == 5)
                {
                    ant[j] = 1;
                }
                else if(j == 6)
                {
                    ant[j] = 2;
                }
                else
                {
                    ant[j] = 0;
                }
            }
            ants[i] = ant;
        }
        FourmiliereModif fourmiliere = new FourmiliereModif(size, 10);
        seeds = fourmiliere.getSeedsArray();
        walls = fourmiliere.getWallsArray();
        ants = fourmiliere.getAntsArray();

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

        Button btnTestEvolu = new Button("Evolution");
        btnTestEvolu.setOnAction(event ->
        {
            //System.out.println("btnTestEvolu start");
            //System.out.println(fourmiliere.stringFourmis());

            fourmiliere.setSeedsArray(board.getSeedsArray());
            fourmiliere.setWallsArray(board.getWallsArray());
            fourmiliere.setAntsArray(board.getAntsArray());

            //System.out.println(fourmiliere.stringFourmis());
            fourmiliere.evolue();
            //System.out.println(fourmiliere.stringFourmis());

            board.setSeedsArray(fourmiliere.getSeedsArray());
            board.setWallsArray(fourmiliere.getWallsArray());
            board.setAntsArray(fourmiliere.getAntsArray());

            //System.out.println(fourmiliere.stringFourmis());

            //System.out.println("btnTestEvolu end");
            board.allDraw();
        });

        BorderPane base = new BorderPane();
        base.setCenter(board);
        base.setRight(controlPanel);
        base.setBottom(btnQuit);
        base.setLeft(btnTestEvolu);


        Scene scene = new Scene(base, 1000, 700);
        primaryStage.setTitle("La Fourmilière");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
