package jeudesfourmis.vue.board;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Board extends Pane
{
    //=== Toutes les couleurs pour les différents affichages de la simulation. ===//
    public static final Color COLOR_ANT = Color.DARKGREEN;
    public static final Color COLOR_ANT_SEED = Color.DODGERBLUE;
    public static final Color COLOR_WALL = Color.BLACK;
    // On utilisera le bleu et le vert pour faire un dégrader pour l'affichage des graines.
    public static final int RGB_SEED_MIN = 50;
    public static final int RGB_SEED_MAX = 255;
    // La couleur rouge pour les graines.
    public static final int RGB_SEED_RED = 255;

    public static final int BOX_SIZE = 10;
    public static final String BOARD_STYLE = "-fx-background-color: ghostwhite; -fx-border-color: black; -fx-border-width: 1px;";
    public static final String LINE_STYLE = "-fx-fill : black;";

    protected final SimpleBooleanProperty displayGridProperty;

    protected final SimpleIntegerProperty sizeBoardProperty;

    protected final SimpleIntegerProperty sizeBoxProperty;

    /**
     * On crée un plateau de taille boardSize.
     *
     * @param displayGrid : si on affiche les cases du plateau ou non.
     * @param boardSize : la taille du plateau.
     * @param boxSize : la taille des cases.
     */
    public Board(boolean displayGrid, int boardSize, int boxSize)
    {
        super();
        this.displayGridProperty = new SimpleBooleanProperty(displayGrid);
        this.sizeBoardProperty = new SimpleIntegerProperty(boardSize);
        this.sizeBoxProperty = new SimpleIntegerProperty(boxSize);

        StringBoardSizeProperty boardStyleProperty =
                new StringBoardSizeProperty(this.sizeBoardProperty, BOARD_STYLE, this.sizeBoxProperty);
        this.styleProperty().bind(boardStyleProperty);
    }

    public Board(boolean displayBoard, int boardSize)
    {
        this(displayBoard, boardSize, BOX_SIZE);
    }

    /**
     * Permet de changer la taille du plateau.
     *
     * @param size : la nouvelle taille du plateau.
     */
    public void setBoardSize(int size)
    {
        if(size > 0)
        {
            this.sizeBoardProperty.setValue(size);
        }
    }

    /**
     * Permet de changer la taille des cases du plateau.
     *
     * @param sizeBox : la nouvelle taille des cases.
     */
    public void setSizeBox(int sizeBox)
    {
        this.sizeBoxProperty.setValue(sizeBox);
    }

    /**
     * Permet de changer l'affichage de la grille, soit oui, soit non.
     */
    public SimpleBooleanProperty getDisplayGridProperty()
    {
        return this.displayGridProperty;
    }

    /**
     * Permet d'afficher certaines lignes du plateau ou non selon la valeur de displayBoardProperty.
     *
     * @param size : la taille du carré que l'on va dessiner.
     */
    public void drawGrid(int size)
    {
        if(this.displayGridProperty.getValue())
        {
            int boxSize = sizeBoxProperty.getValue();
            // Lignes horizontales.
            for(int i = 0; i <= size; i++)
            {
                CustomLine line = new CustomLine(0, i * boxSize, size * boxSize, i * boxSize,
                        LINE_STYLE, this);
            }
            // Lignes verticales.
            for(int j = 0; j <= size; j++)
            {
                CustomLine line = new CustomLine(j * boxSize, 0, j * boxSize, size * boxSize,
                        LINE_STYLE, this);
            }
        }
    }

    /**
     * Permet d'afficher les lignes de tout le plateau ou non selon la valeur de sizeBoardProperty avec les valeurs
     *  par defaut.
     */
    public void drawGrid()
    {
        int size = this.sizeBoardProperty.getValue();
        this.drawGrid(size);
    }

    /**
     * Permet d'afficher certaines graines sur le plateau, plus il y a de graines plus la couleur est intense.
     *
     *  @param seedsArray : le tableau de graines à afficher.
     *  @param qMax : le nombre maximal de graines sur une case.
     *  @param x : la position x par laquelle on commence à dessiner.
     *  @param y : la position y par laquelle on commence à dessiner.
     *  @param size : la taille du carré que l'on va dessiner.
     *
     */
    public void drawSeedsOnBoard(int[][] seedsArray, int qMax, int x, int y, int size)
    {
        int boxSize = sizeBoxProperty.getValue();
        int colorIncrement = (RGB_SEED_MAX - RGB_SEED_MIN) / qMax;
        if(colorIncrement < 1)
        {
            colorIncrement = 1;
        }
        // 0 11
        for(int i = x; i < size + x; i++)
        {
            int iModif = i - x;
            // 11 11
            for(int j = y; j < size + y; j++)
            {
                int nbSeedInBox = seedsArray[i][j];
                if(nbSeedInBox > 0)
                {
                    int jModif = j - y;
                    int greenAndBlueLevel = colorIncrement * (qMax - nbSeedInBox);
                    Color colorSeed = Color.rgb(RGB_SEED_RED, greenAndBlueLevel, greenAndBlueLevel);
                    // Sans le +0.5 : problème de rendu.
                    Rectangle seeds = new Rectangle(boxSize + 0.5, boxSize + 0.5, colorSeed);
                    seeds.setMouseTransparent(true);
                    seeds.setX(iModif * boxSize);
                    seeds.setY(jModif * boxSize);
                    this.getChildren().add(seeds);
                }
            }
        }
    }

    /**
     * Permet d'afficher toutes les graines sur le plateau avec les valeurs par defauts,
     * plus il y a de graines plus la couleur est intense.
     */
    public void drawSeedsOnBoard(int[][] seedsArray, int qMax)
    {
        int arraySizeXY = seedsArray.length;
        this.drawSeedsOnBoard(seedsArray, qMax, 0, 0, arraySizeXY);
    }

    /**
     * Permet d'afficher certains des murs sur le plateau.
     *
     * @param wallsArray : le tableau de graines à afficher.
     * @param x : la position x par laquelle on commence à dessiner.
     * @param y : la position y par laquelle on commence à dessiner.
     * @param size : la taille du carré que l'on va dessiner.
     */
    public void drawWallsOnBoard(boolean[][] wallsArray, int x, int y, int size)
    {
        int boxSize = sizeBoxProperty.getValue();
        for(int i = x; i < size + x; i++)
        {
            int iModif = i - x;
            for(int j = y; j < size + y; j++)
            {
                if(wallsArray[i][j])
                {
                    int jModif = j - y;
                    // Sans le +0.5 : problème de rendu.
                    Rectangle wall = new Rectangle(boxSize + 0.5, boxSize + 0.5, COLOR_WALL);
                    wall.setMouseTransparent(true);
                    wall.setX(iModif * boxSize);
                    wall.setY(jModif * boxSize);
                    this.getChildren().add(wall);
                }
            }
        }
    }

    /**
     * Permet d'afficher tous les murs sur le plateau avec les valeurs par defauts.
     */
    public void drawWallsOnBoard(boolean[][] wallsArray)
    {
        int arraySizeXY = wallsArray.length;
        this.drawWallsOnBoard(wallsArray, 0, 0, arraySizeXY);
    }

    /**
     * Permet d'afficher certaines des fourmis sur le plateau.
     *
     * @param antsArray : le tableau de fourmis à afficher.
     *                  (0: pas de fourmi, 1: fourmi sans graine, 2: fourmi avec graine)
     * @param x : la position x par laquelle on commence à dessiner.
     * @param y : la position y par laquelle on commence à dessiner.
     * @param size : la taille du carré que l'on va dessiner.
     */
    public void drawAntsOnBoard(int[][] antsArray, int x, int y, int size)
    {
        int boxSize = sizeBoxProperty.getValue();
        for(int i = x; i < size + x; i++)
        {
            int iModif = i - x;
            for(int j = y; j < size + y; j++)
            {
                int antState = antsArray[i][j];
                if(antState == 1 || antState == 2)
                {
                    int jModif = j - y;
                    Color antColor = antState == 1 ? COLOR_ANT : COLOR_ANT_SEED;
                    int toCenter = boxSize / 2;
                    Circle ant = new Circle(iModif * boxSize + toCenter, jModif * boxSize + toCenter,
                            boxSize / 2, antColor);
                    ant.setMouseTransparent(true);
                    this.getChildren().add(ant);
                }
            }
        }
    }

    /**
     * Permet d'afficher toutes des fourmis sur le plateau.
     */
    public void drawAntsOnBoard(int[][] antsArray)
    {
        int arraySizeXY = antsArray.length;
        this.drawAntsOnBoard(antsArray, 0, 0, arraySizeXY);
    }

    /**
     * Permet de supprimer tout ce qu'il y a sur le plateau.
     */
    public void clearBoard()
    {
        this.getChildren().clear();
    }

    /**
     * Permet d'afficher sur le plateau tous les elements (graines, fourmis et murs) avec un zoom sur le plateau,
     * avec une zone allant de minXY à maxXY.
     *
     * @param seedsArray : le tableau de graines à afficher.
     * @param qMax : le nombre maximal de graines sur une case.
     * @param wallsArray : le tableau de graines à afficher.
     * @param antsArray : le tableau de fourmis à afficher.
     *                  (0: pas de fourmi, 1: fourmi sans graine, 2: fourmi avec graine)
     * @param x : la position x par laquelle on commence à dessiner.
     * @param y : la position y par laquelle on commence à dessiner.
     * @param size : la taille du carré que l'on va dessiner.
     */
    public void allDraw(int[][] seedsArray, int qMax, boolean[][] wallsArray, int[][] antsArray, int x, int y, int size)
    {
        this.clearBoard();
        this.drawSeedsOnBoard(seedsArray, qMax, x, y, size);
        this.drawWallsOnBoard(wallsArray, x, y, size);
        this.drawAntsOnBoard(antsArray, x, y, size);
        this.drawGrid(size);
    }

    /**
     * Permet d'afficher sur le plateau tous les elements (graines, fourmis et murs).
     *
     * @param seedsArray : le tableau de graines à afficher.
     * @param qMax : le nombre maximal de graines sur une case.
     * @param wallsArray : le tableau de graines à afficher.
     * @param antsArray : le tableau de fourmis à afficher.
     *                   (0: pas de fourmi, 1: fourmi sans graine, 2: fourmi avec graine)
     */
    public void allDraw(int[][] seedsArray, int qMax, boolean[][] wallsArray, int[][] antsArray)
    {
        this.clearBoard();
        this.drawSeedsOnBoard(seedsArray, qMax);
        this.drawWallsOnBoard(wallsArray);
        this.drawAntsOnBoard(antsArray);
        this.drawGrid();
    }
}
