package jeudesfourmis.vue.tests.grid;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Grid extends Pane
{
    //=== Toutes les couleurs pour les différents affichage de la simulation. ===//
    public static final Color COLOR_ANT = Color.DARKGREEN;
    public static final Color COLOR_ANT_SEED = Color.DODGERBLUE;
    public static final Color COLOR_WALL = Color.BLACK;
    public static final Color COLOR_EMPTY = Color.WHITE;
    // On utilisera le bleu et le vert pour faire un dégreder pour l'affichage des graines.
    public static final int RGB_SEED_MIN = 50;
    public static final int RGB_SEED_MAX = 255;
    // La couleur rouge pour les graines.
    public static final int RGB_SEED_RED = 255;
    public static final Color COLOR_BACKGROUND_GRID = Color.GHOSTWHITE;

    public static final int BOX_SIZE = 25;
    public static final int GRID_SIZE_MIN = 20;
    public static final String GRID_STYLE = "-fx-background-color: ghostwhite;";
    public static final String LINE_STYLE = "-fx-fill : black;";

    private SimpleBooleanProperty displayGridProperty;
    private SimpleIntegerProperty widthGridProperty;
    private SimpleIntegerProperty heightGridProperty;

    /**
     * On crée une grille de taille gridSize.
     *
     * @param displayGrid : si on affiche les cases de la grille ou non.
     * @param gridSize : la taille de la grille.
     */
    public Grid(boolean displayGrid, int gridSize)
    {
        super();
        this.displayGridProperty = new SimpleBooleanProperty(displayGrid);
        this.widthGridProperty = new SimpleIntegerProperty(gridSize);
        this.heightGridProperty = new SimpleIntegerProperty(gridSize);

        StringGridSizeProterty gridStyleProperty =
                new StringGridSizeProterty(this.widthGridProperty, this.heightGridProperty, GRID_STYLE, BOX_SIZE);
        this.styleProperty().bind(gridStyleProperty);

        this.drawGrid();
    }

    /**
     * Permet de changer la taille de la grille mais pas en dessous de 20.
     *
     * @param size : la nouvelle taille de la grille.
     */
    public void setGridSize(int size)
    {
        if(size >= GRID_SIZE_MIN)
        {
            this.widthGridProperty.setValue(size);
            this.heightGridProperty.setValue(size);
        }
    }

    /**
     * Permet d'afficher les lignes de la grille ou non selon la valeur de displayGridProperty.
     */
    public void drawGrid()
    {
        if(this.displayGridProperty.getValue())
        {
            int width = this.widthGridProperty.getValue();
            int height = this.heightGridProperty.getValue();
            // Lignes horizontales.
            for(int i = 0; i <= width; i++)
            {
                CustomLine line = new CustomLine(0, i * BOX_SIZE, width * BOX_SIZE, i * BOX_SIZE,
                        LINE_STYLE, this);
            }
            // Lignes verticales.
            for(int j = 0; j <= height; j++)
            {
                CustomLine line = new CustomLine(j * BOX_SIZE, 0, j * BOX_SIZE, height * BOX_SIZE,
                        LINE_STYLE, this);
            }
        }
    }

    /**
     * Permet d'afficher les graines sur la grille, plus il y a de graines plus la couleur est intense.
     *
     *  @param seedsMaze : le tableau de graines à afficher.
     *  @param qMax : le nombre maximal de graines sur une case.
     */
    public void drawSeedsOnGrid(int[][] seedsMaze, int qMax)
    {
        int mazeSizeX = seedsMaze.length;
        int mazeSizeY = seedsMaze[0].length;

        int colorIncrement = (RGB_SEED_MAX - RGB_SEED_MIN) / qMax;
        if(colorIncrement < 1)
        {
            colorIncrement = 1;
        }

        for(int i = 0; i < mazeSizeX; i++)
        {
            for(int j = 0; j < mazeSizeY; j++)
            {
                int nbSeedInBox = seedsMaze[i][j];
                if(nbSeedInBox > 0)
                {
                    int greenAndBlueLevel = colorIncrement * (qMax - nbSeedInBox);
                    Color colorSeed = Color.rgb(RGB_SEED_RED, greenAndBlueLevel, greenAndBlueLevel);
                    // Sans le +0.5 : problème de rendu.
                    Rectangle seeds = new Rectangle(BOX_SIZE + 0.5, BOX_SIZE + 0.5, colorSeed);
                    seeds.setX(i * BOX_SIZE);
                    seeds.setY(j * BOX_SIZE);
                    this.getChildren().add(seeds);
                }
            }
        }
    }

    /**
     * Permet d'afficher les murs sur la grille.
     *
     * @param wallsMaze : le tableau de graines à afficher.
     */
    public void drawWallsOnGrid(boolean[][] wallsMaze)
    {
        int mazeSizeX = wallsMaze.length;
        int mazeSizeY = wallsMaze[0].length;

        for(int i = 0; i < mazeSizeX; i++)
        {
            for(int j = 0; j < mazeSizeY; j++)
            {
                if(wallsMaze[i][j])
                {
                    // Sans le +0.5 : problème de rendu.
                    Rectangle wall = new Rectangle(BOX_SIZE + 0.5, BOX_SIZE + 0.5, COLOR_WALL);
                    wall.setX(i * BOX_SIZE);
                    wall.setY(j * BOX_SIZE);
                    this.getChildren().add(wall);
                }
            }
        }
    }

    /**
     * Permet d'afficher les fourmis sur la grille.
     *
     * @param antsMaze : le tableau de fourmis à afficher.
     */
    public void drawAntsOnGrid(int[][] antsMaze)
    {
        int mazeSizeX = antsMaze.length;
        int mazeSizeY = antsMaze[0].length;

        for(int i = 0; i < mazeSizeX; i++)
        {
            for(int j = 0; j < mazeSizeY; j++)
            {
                int antState = antsMaze[i][j];
                if(antState == 1 || antState == 2)
                {
                    Color antColor = antState == 1 ? COLOR_ANT : COLOR_ANT_SEED;
                    int toCenter = BOX_SIZE / 2;
                    Circle ant = new Circle(i * BOX_SIZE + toCenter, j * BOX_SIZE + toCenter,
                            BOX_SIZE / 2, antColor);
                    this.getChildren().add(ant);
                }
            }
        }
    }

    /**
     * Permet de supprimer tout ce qu'il y a sur la grille.
     */
    public void clearGrid()
    {
        this.getChildren().clear();
    }

}
