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
    private SimpleIntegerProperty sizeBoxProperty;

    /**
     * On crée une grille de taille gridSize.
     *
     * @param displayGrid : si on affiche les cases de la grille ou non.
     * @param gridSize : la taille de la grille.
     * @param boxSize : la taille des cases.
     */
    public Grid(boolean displayGrid, int gridSize, int boxSize)
    {
        super();
        this.displayGridProperty = new SimpleBooleanProperty(displayGrid);
        this.widthGridProperty = new SimpleIntegerProperty(gridSize);
        this.heightGridProperty = new SimpleIntegerProperty(gridSize);
        this.sizeBoxProperty = new SimpleIntegerProperty(boxSize);

        StringGridSizeProterty gridStyleProperty =
                new StringGridSizeProterty(this.widthGridProperty, this.heightGridProperty, GRID_STYLE, this.sizeBoxProperty);
        this.styleProperty().bind(gridStyleProperty);
    }

    public Grid(boolean displayGrid, int gridSize)
    {
        this(displayGrid, gridSize, BOX_SIZE);
    }

    /**
     * Permet de changer la taille de la grille mais pas en dessous de 20.
     *
     * @param size : la nouvelle taille de la grille.
     */
    public void setGridSize(int size)
    {
        /*
        if(size >= GRID_SIZE_MIN)
        {
        */
            this.widthGridProperty.setValue(size);
            this.heightGridProperty.setValue(size);
        //}
    }

    /**
     * Permet de changer la taille des cases de la grille.
     *
     * @param sizeBox : la nouvelle taille des cases.
     */
    public void setSizeBox(int sizeBox)
    {
        this.sizeBoxProperty.setValue(sizeBox);
    }

    /**
     * Permet d'afficher certaines lignes de la grille ou non selon la valeur de displayGridProperty.
     *
     * @param minX : le minimum de la boucle sur l'axe X.
     * @param minY : le minimum de la boucle sur l'axe Y.
     * @param maxX : le maximum de la boucle sur l'axe X.
     * @param maxY : le maximum de la boucle sur l'axe Y.
     */
    public void drawGrid(int minX, int minY, int maxX, int maxY)
    {
        if(this.displayGridProperty.getValue())
        {
            int boxSize = sizeBoxProperty.getValue();
            // Lignes horizontales.
            for(int i = minX; i <= maxX; i++)
            {
                int iModif = i - minX;
                int maxXModif = maxX - minX;
                CustomLine line = new CustomLine(0, iModif * boxSize, maxXModif * boxSize, iModif * boxSize,
                        LINE_STYLE, this);
            }
            // Lignes verticales.
            for(int j = minY; j <= maxY; j++)
            {
                int jModif = j - minY;
                int maxYModif = maxY - minY;
                CustomLine line = new CustomLine(jModif * boxSize, 0, jModif * boxSize, maxYModif * boxSize,
                        LINE_STYLE, this);
            }
        }
    }

    /**
     * Permet d'afficher les lignes de toute la grille ou non selon la valeur de displayGridProperty avec les valeurs
     *  par defaut.
     */
    public void drawGrid()
    {
        int width = this.widthGridProperty.getValue();
        int height = this.heightGridProperty.getValue();
        this.drawGrid(0, 0, width, height);
    }

    /**
     * Permet d'afficher certaines graines sur la grille, plus il y a de graines plus la couleur est intense.
     *
     *  @param seedsArray : le tableau de graines à afficher.
     *  @param qMax : le nombre maximal de graines sur une case.
     *  @param minX : le minimum de la boucle sur l'axe X.
     *  @param minY : le minimum de la boucle sur l'axe Y.
     *  @param maxX : le maximum de la boucle sur l'axe X.
     *  @param maxY : le maximum de la boucle sur l'axe Y.
     *
     */
    public void drawSeedsOnGrid(int[][] seedsArray, int qMax, int minX, int minY, int maxX, int maxY)
    {
        int boxSize = sizeBoxProperty.getValue();
        int colorIncrement = (RGB_SEED_MAX - RGB_SEED_MIN) / qMax;
        if(colorIncrement < 1)
        {
            colorIncrement = 1;
        }

        for(int i = minX; i < maxX; i++)
        {
            int iModif = i - minX;
            for(int j = minY; j < maxY; j++)
            {
                int nbSeedInBox = seedsArray[i][j];
                if(nbSeedInBox > 0)
                {
                    int jModif = j - minY;
                    int greenAndBlueLevel = colorIncrement * (qMax - nbSeedInBox);
                    Color colorSeed = Color.rgb(RGB_SEED_RED, greenAndBlueLevel, greenAndBlueLevel);
                    // Sans le +0.5 : problème de rendu.
                    Rectangle seeds = new Rectangle(boxSize + 0.5, boxSize + 0.5, colorSeed);
                    seeds.setX(iModif * boxSize);
                    seeds.setY(jModif * boxSize);
                    this.getChildren().add(seeds);
                }
            }
        }
    }

    /**
     * Permet d'afficher toutes les graines sur la grille avec les valeurs par defauts,
     * plus il y a de graines plus la couleur est intense.
     */
    public void drawSeedsOnGrid(int[][] seedsArray, int qMax)
    {
        int arraySizeX = seedsArray.length;
        int arraySizeY = seedsArray[0].length;
        this.drawSeedsOnGrid(seedsArray, qMax, 0, 0, arraySizeX, arraySizeY);
    }

    /**
     * Permet d'afficher certains des murs sur la grille.
     *
     * @param wallsArray : le tableau de graines à afficher.
     * @param minX : le minimum de la boucle sur l'axe X.
     * @param minY : le minimum de la boucle sur l'axe Y.
     * @param maxX : le maximum de la boucle sur l'axe X.
     * @param maxY : le maximum de la boucle sur l'axe Y.
     */
    public void drawWallsOnGrid(boolean[][] wallsArray, int minX, int minY, int maxX, int maxY)
    {
        int boxSize = sizeBoxProperty.getValue();
        for(int i = minX; i < maxX; i++)
        {
            int iModif = i - minX;
            for(int j = minY; j < maxY; j++)
            {
                if(wallsArray[i][j])
                {
                    int jModif = j - minY;
                    // Sans le +0.5 : problème de rendu.
                    Rectangle wall = new Rectangle(boxSize + 0.5, boxSize + 0.5, COLOR_WALL);
                    wall.setX(iModif * boxSize);
                    wall.setY(jModif * boxSize);
                    this.getChildren().add(wall);
                }
            }
        }
    }

    /**
     * Permet d'afficher tous les murs sur la grille avec les valeurs par defauts.
     */
    public void drawWallsOnGrid(boolean[][] wallsArray)
    {
        int arraySizeX = wallsArray.length;
        int arraySizeY = wallsArray[0].length;
        this.drawWallsOnGrid(wallsArray, 0, 0, arraySizeX, arraySizeY);
    }

    /**
     * Permet d'afficher certaines des fourmis sur la grille.
     *
     * @param antsArray : le tableau de fourmis à afficher.
     *                  (0: pas de fourmi, 1: fourmi sans graine, 2: fourmi avec graine)
     * @param minX : le minimum de la boucle sur l'axe X.
     * @param minY : le minimum de la boucle sur l'axe Y.
     * @param maxX : le maximum de la boucle sur l'axe X.
     * @param maxY : le maximum de la boucle sur l'axe Y.
     */
    public void drawAntsOnGrid(int[][] antsArray, int minX, int minY, int maxX, int maxY)
    {
        int boxSize = sizeBoxProperty.getValue();
        for(int i = minX; i < maxX; i++)
        {
            int iModif = i - minX;
            for(int j = minY; j < maxY; j++)
            {
                int antState = antsArray[i][j];
                if(antState == 1 || antState == 2)
                {
                    int jModif = j - minY;
                    Color antColor = antState == 1 ? COLOR_ANT : COLOR_ANT_SEED;
                    int toCenter = boxSize / 2;
                    Circle ant = new Circle(iModif * boxSize + toCenter, jModif * boxSize + toCenter,
                            boxSize / 2, antColor);
                    this.getChildren().add(ant);
                }
            }
        }
    }

    /**
     * Permet d'afficher toutes des fourmis sur la grille.
     */
    public void drawAntsOnGrid(int[][] antsArray)
    {
        int arraySizeX = antsArray.length;
        int arraySizeY = antsArray[0].length;
        this.drawAntsOnGrid(antsArray, 0, 0, arraySizeX, arraySizeY);
    }

    /**
     * Permet de supprimer tout ce qu'il y a sur la grille.
     */
    public void clearGrid()
    {
        this.getChildren().clear();
    }

}
