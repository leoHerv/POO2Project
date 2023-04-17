package jeudesfourmis.vue.tests.grid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestGrid extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        int start = 0;
        int end = 10;


        Grid grid = new Grid(true, 10);
        grid.setGridSize(end - start);

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
        grid.drawSeedsOnGrid(seeds, 10, start, start, end, end);
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
        grid.drawWallsOnGrid(walls, start, start, end, end);
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
        grid.drawAntsOnGrid(ants, start, start, end, end);
        grid.drawGrid(start, start, end, end);

        //grid.clearGrid();

        BorderPane base = new BorderPane();
        base.setCenter(grid);

        Scene scene = new Scene(base, 700, 600);

        primaryStage.setTitle("Test Grid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
