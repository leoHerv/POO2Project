package jeudesfourmis.controller;

import javafx.beans.property.SimpleBooleanProperty;
import jeudesfourmis.model.FourmiliereModif;
import jeudesfourmis.vue.board.BoardWithZoomBoard;

public class UpdateBoardAndAnthill
{
    public static int[][] reSizeArray(int[][] array, int oldSize, int newSize)
    {
        if(oldSize == newSize)
        {
            return array;
        }
        else if(newSize > 0)
        {
            int[][] newArray = new int[newSize][newSize];
            for(int i = 0; i < newSize; i++)
            {
                for(int j = 0; j < newSize; j++)
                {
                    if(i < oldSize && j < oldSize)
                    {
                        newArray[i][j] = array[i][j];
                    }
                    else
                    {
                        newArray[i][j] = 0;
                    }
                }
            }
            return newArray;
        }
        return null;
    }

    public static boolean[][] reSizeArray(boolean[][] array, int oldSize, int newSize)
    {
        if(oldSize == newSize)
        {
            return array;
        }
        else if(newSize > 0)
        {
            boolean[][] newArray = new boolean[newSize][newSize];
            for(int i = 0; i < newSize; i++)
            {
                for(int j = 0; j < newSize; j++)
                {
                    if(i < oldSize && j < oldSize)
                    {
                        newArray[i][j] = array[i][j];
                    }
                    else
                    {
                        newArray[i][j] = false;
                    }
                }
            }
            return newArray;
        }
        return null;
    }

    public static void resetBoardAndAnthill(FourmiliereModif anthill, BoardWithZoomBoard board, SimpleBooleanProperty resetProperty)
    {
        if(resetProperty.getValue())
        {
            anthill.setNewFourmiliere(new FourmiliereModif(anthill.getHauteur() + 2, anthill.getQMax()));

            board.setSeedsArray(anthill.getSeedsArray());
            board.setWallsArray(anthill.getWallsArray());
            board.setAntsArray(anthill.getAntsArray());

            board.allDraw();

            resetProperty.setValue(false);
        }
    }

    public static void changeSizeBoardAndAnthill(int newSize, FourmiliereModif anthill, BoardWithZoomBoard board,
                                          SimpleBooleanProperty changeSizeProperty)
    {
        if(changeSizeProperty.getValue())
        {
            int oldSize = board.getAntsArray().length;

            int[][] newSeedsArray = reSizeArray(board.getSeedsArray(), oldSize, newSize);
            boolean[][] newWallsArray = reSizeArray(board.getWallsArray(), oldSize, newSize);
            int[][] newAntsArray = reSizeArray(board.getAntsArray(), oldSize, newSize);

            System.out.println("après: " + newSeedsArray.length + " " + newWallsArray.length);

            anthill.setNewFourmiliere(new FourmiliereModif(newSize, anthill.getQMax()));
            System.out.println(anthill.getHauteur() + " " + anthill.getLargeur() + " size: " + newSize);

            board.setBoardSize(newSize);
            board.setSeedsArray(newSeedsArray);
            board.setWallsArray(newWallsArray);
            board.setAntsArray(newAntsArray);

            anthill.setSeedsArray(newSeedsArray);
            anthill.setWallsArray(newWallsArray);
            anthill.setAntsArray(newAntsArray);

            board.allDraw();

            changeSizeProperty.setValue(false);
        }
    }

}
