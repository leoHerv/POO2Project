package jeudesfourmis.vue.tests.grid;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestGrid extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        int start = 1;
        int end = 7;
        int size = 30;

        /*
        int[][] seeds = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};*/
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
        //grid.drawSeedsOnGrid(seeds, 10, start, start, end, end);
        /*
        boolean[][] walls = {{false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {true, true, true, true, true, true, true, true, true, true},
                            {true, true, true, true, true, true, true, true, true, true}};*/

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
        //grid.drawWallsOnGrid(walls, start, start, end, end);
        /*
        int[][] ants = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 2, 0, 2, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};*/
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
        //grid.drawAntsOnGrid(ants, start, start, end, end);
        //grid.drawGrid(start, start, end, end);

        GridEditable grid = new GridEditable(true, size, seeds, 10, walls, ants);
        //grid.setSizeBox(20);
       //grid.allDraw();

        //grid.clearGrid();

        BorderPane base = new BorderPane();
        base.setCenter(grid);

        Scene scene = new Scene(base, 700, 600);

        primaryStage.setTitle("Test Grid");
        primaryStage.setScene(scene);
        primaryStage.show();


        /////////////////////////////////
        Grid gridClone = new Grid(true, 11, 20);


        ChangeListener<Number> xyMove = new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                int posX = grid.getPosPointerX().getValue();
                int posY = grid.getPosPointerY().getValue();

                System.out.println(posX + " " + posY);

                if(posX < 6)
                {
                    posX = 5;
                }
                if(posY < 6)
                {
                    posY = 5;
                }
                if(posX > size - 6)
                {
                    posX = size - 6;
                }
                if(posY >= size - 6)
                {
                    posY = size - 6;
                }

                gridClone.allDraw(seeds, 10, walls, ants, posX - 5, posY - 5, 11);
            }
        };

        grid.getPosPointerX().addListener(xyMove);
        grid.getPosPointerY().addListener(xyMove);

        BorderPane autreBase = new BorderPane();
        autreBase.setCenter(gridClone);

        Scene autreScene = new Scene(autreBase, 500, 400);

        Stage autreStage = new Stage();
        autreStage.setTitle("Autre scene");
        autreStage.setScene(autreScene);
        autreStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
