package jeuDesFourmis.vue.board;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestGrid extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        /*
        int start = 1;
        int end = 7;
        int size = 30;


        int[][] seeds = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
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

        boolean[][] walls = {{false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {false, false, false, false, false, false, false, false, false, false},
                            {true, true, true, true, true, true, true, true, true, true},
                            {true, true, true, true, true, true, true, true, true, true}};

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

        int[][] ants = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 2, 0, 2, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
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

        SimpleBooleanProperty bp = new SimpleBooleanProperty(false);

        //BoardEditable grid = new BoardWithZoomBoard(true, size, seeds, 10, walls, ants, bp);
        //grid.setSizeBox(20);
       //grid.allDraw();

        //grid.clearGrid();

        Button btnZoom = new Button("Zoom");
        btnZoom.setOnAction(event ->
        {
            bp.setValue(!bp.getValue());
        });

        BorderPane base = new BorderPane();
        base.setCenter(grid);
        base.setRight(btnZoom);

        Scene scene = new Scene(base, 700, 600);

        primaryStage.setTitle("Test Board");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
