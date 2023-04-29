package jeudesfourmis.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import jeudesfourmis.model.FourmiliereModif;
import jeudesfourmis.vue.board.BoardWithZoomBoard;

import java.util.Random;

public class UpdateBoardAndAnthill
{
    public static final Random RANDOM = new Random();

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

    public static int[][] arrayRespectLimiteMax(int[][] array, int limite)
    {
        if(limite > 0)
        {
            int size = array.length;
            for(int i = 0; i < size; i++)
            {
                for(int j = 0; j < size; j++)
                {
                    if(array[i][j] >= limite)
                    {
                        array[i][j] = limite - 1;
                    }
                }
            }
        }
        return array;
    }

    public static void resetBoardAndAnthill(FourmiliereModif anthill, BoardWithZoomBoard board, SimpleBooleanProperty resetProperty)
    {
        if(resetProperty.getValue())
        {
            anthill.setNewFourmiliere(new FourmiliereModif(anthill.getHauteur(), anthill.getQMax()));

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

            anthill.setNewFourmiliere(new FourmiliereModif(newSize, anthill.getQMax()));

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

    public static void changeQMaxBoardAndAnthill(int newQMax, FourmiliereModif anthill, BoardWithZoomBoard board)
    {
        anthill.setNewFourmiliere(new FourmiliereModif(anthill.getHauteur(), newQMax));

        anthill.setSeedsArray(arrayRespectLimiteMax(board.getSeedsArray(), newQMax));
        anthill.setWallsArray(board.getWallsArray());
        anthill.setAntsArray(board.getAntsArray());

        board.setqMax(newQMax);

        board.allDraw();
    }

    public static int getRandomElement(SimpleIntegerProperty probaSeedsProperty, SimpleIntegerProperty probaWallsProperty,
                                       SimpleIntegerProperty probaAntsProperty, SimpleIntegerProperty probaEmptyProperty)
    {
        int seedsValue = probaSeedsProperty.getValue();
        int wallsValue = probaWallsProperty.getValue();
        int antsValue = probaAntsProperty.getValue();
        int emptyValue = probaEmptyProperty.getValue();

        int randomNb = RANDOM.nextInt(seedsValue + wallsValue + antsValue + emptyValue);
        if(randomNb < seedsValue)
        {
            // For a seed
            return 0;
        }
        else if(randomNb < seedsValue + wallsValue)
        {
            // For a wall
            return 1;
        }
        else if(randomNb < seedsValue + wallsValue + antsValue)
        {
            // For a ant
            return 2;
        }
        else
        {
            // For an empty box
            return 3;
        }
    }

    public static void getRandomMap(FourmiliereModif anthill, BoardWithZoomBoard board, SimpleIntegerProperty probaSeedsProperty,
                                    SimpleIntegerProperty probaWallsProperty, SimpleIntegerProperty probaAntsProperty,
                                    SimpleBooleanProperty randMapProperty, SimpleIntegerProperty probaEmptyProperty)
    {
        if(randMapProperty.getValue())
        {
            if(probaSeedsProperty.getValue() + probaWallsProperty.getValue() + probaAntsProperty.getValue() ==0)
            {
                resetBoardAndAnthill(anthill, board, randMapProperty);
                return;
            }
            int[][] seedsArray = board.getSeedsArray();
            boolean[][] wallsArray = board.getWallsArray();
            int[][] antsArray = board.getAntsArray();

            int size = board.getBoardSize();

            int qMax = anthill.getQMax();

            for(int i = 0; i < size; i++)
            {
                for(int j = 0; j < size; j++)
                {
                    int randElement = getRandomElement(probaSeedsProperty, probaWallsProperty,
                            probaAntsProperty, probaEmptyProperty);
                    if(randElement == 0)
                    {
                        seedsArray[i][j] = RANDOM.nextInt(qMax);
                        wallsArray[i][j] = false;
                        antsArray[i][j] = 0;
                    }
                    else if(randElement == 1)
                    {
                        seedsArray[i][j] = 0;
                        wallsArray[i][j] = true;
                        antsArray[i][j] = 0;
                    }
                    else if(randElement == 2)
                    {
                        seedsArray[i][j] = 0;
                        wallsArray[i][j] = false;
                        antsArray[i][j] = 1;
                    }
                    else
                    {
                        seedsArray[i][j] = 0;
                        wallsArray[i][j] = false;
                        antsArray[i][j] = 0;
                    }
                }
            }

            anthill.setSeedsArray(seedsArray);
            anthill.setWallsArray(wallsArray);
            anthill.setAntsArray(antsArray);

            board.allDraw();
        }
    }

}
