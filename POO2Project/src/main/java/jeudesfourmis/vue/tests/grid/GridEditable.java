package jeudesfourmis.vue.tests.grid;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class GridEditable extends Grid
{
    protected int[][] seedsArray;
    protected int qMax;
    protected boolean[][] wallsArray;
    protected int[][] antsArray;

    // Pour ajouter des elements sur le plateau.
    protected boolean isCurrentlyAddWall;
    protected boolean isCurrentlyRemoveWall;
    protected boolean isCurrentlyAddAnt;
    protected boolean isCurrentlyRemoveAnt;

    protected SimpleBooleanProperty isEditableProperty;

    protected SimpleIntegerProperty posPointerX;
    protected SimpleIntegerProperty posPointerY;

    public GridEditable(boolean displayGrid, int gridSize, int[][] seedsArray, int qMax, boolean[][] wallsArray, int[][] antsArray)
    {
        super(displayGrid, gridSize);
        this.seedsArray = seedsArray;
        this.qMax = qMax;
        this.wallsArray = wallsArray;
        this.antsArray = antsArray;

        this.isCurrentlyAddWall = false;
        this.isCurrentlyRemoveWall = false;
        this.isCurrentlyAddAnt = false;
        this.isCurrentlyRemoveAnt = false;

        this.isEditableProperty = new SimpleBooleanProperty(true);

        this.posPointerX = new SimpleIntegerProperty(gridSize / 2);
        this.posPointerY = new SimpleIntegerProperty(gridSize / 2);

        EventHandler<MouseEvent> handleStartDrag = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(isEditableProperty.getValue())
                {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    int caseX = x / sizeBoxProperty.getValue();
                    int caseY = y / sizeBoxProperty.getValue();

                    int sizeGrid = sizeGridProperty.getValue();

                    if(caseX >= 0 && caseY >= 0 && caseX < sizeGrid && caseY < sizeGrid)
                    {
                        if(event.isPrimaryButtonDown())
                        {
                            if(event.isShiftDown())
                            {
                                if(!haveAnt(caseX, caseY))
                                {
                                    isCurrentlyAddAnt = true;
                                    isCurrentlyRemoveWall = true;
                                }
                                else
                                {
                                    isCurrentlyRemoveAnt = true;
                                }
                            }
                            else
                            {
                                if(!haveWall(caseX, caseY))
                                {
                                    isCurrentlyAddWall = true;
                                    isCurrentlyRemoveAnt = true;
                                }
                                else
                                {
                                    isCurrentlyRemoveWall = true;
                                }
                            }
                        }
                        drawOnGrid(caseX, caseY, event);
                    }
                }
            }
        };

        EventHandler<MouseEvent> handleEndDrag = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                isCurrentlyAddWall = false;
                isCurrentlyRemoveWall = false;
                isCurrentlyAddAnt = false;
                isCurrentlyRemoveAnt = false;
            }
        };
        EventHandler<MouseEvent> handleOnDrag = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(isEditableProperty.getValue())
                {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    int caseX = x / sizeBoxProperty.getValue();
                    int caseY = y / sizeBoxProperty.getValue();

                    int sizeGrid = sizeGridProperty.getValue();

                    if(caseX >= 0 && caseY >= 0 && caseX < sizeGrid && caseY < sizeGrid)
                    {
                        drawOnGrid(caseX, caseY, event);
                    }
                }
            }
        };

        EventHandler<MouseEvent> handlePosPointer = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                int x = (int) event.getX();
                int y = (int) event.getY();

                int caseX = x / sizeBoxProperty.getValue();
                int caseY = y / sizeBoxProperty.getValue();

                posPointerX.setValue(caseX);
                posPointerY.setValue(caseY);
            }
        };

        EventHandler<ScrollEvent> handleOnScroll = new EventHandler<>()
        {
            @Override
            public void handle(ScrollEvent event)
            {
                int x = (int) event.getX();
                int y = (int) event.getY();

                int caseX = x / sizeBoxProperty.getValue();
                int caseY = y / sizeBoxProperty.getValue();

                int sizeGrid = sizeGridProperty.getValue();

                int yScroll = (int) event.getDeltaY();

                if(caseX >= 0 && caseY >= 0 && caseX < sizeGrid && caseY < sizeGrid && yScroll != 0)
                {
                    if(yScroll > 0)
                    {
                        setSeeds(caseX, caseY, 1);
                    }
                    if(yScroll < 0)
                    {
                        setSeeds(caseX, caseY, -1);
                    }
                    allDraw();
                }
            }
        };

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, handleStartDrag);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, handleEndDrag);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, handleOnDrag);

        this.addEventHandler(MouseEvent.MOUSE_MOVED, handlePosPointer);

        this.addEventHandler(ScrollEvent.SCROLL, handleOnScroll);
    }

    /**
     * Retourne isEditableProperty pour pouvoir modifier si la grille doit être modifiable ou non.
     */
    public SimpleBooleanProperty getIsEditableProperty()
    {
        return this.isEditableProperty;
    }

    /**
     * Retourne posPointerX pour avoir la position en X de la souris.
     */
    public SimpleIntegerProperty getPosPointerX()
    {
        return this.posPointerX;
    }

    /**
     * Retourne posPointerX pour avoir la position en Y de la souris.
     */
    public SimpleIntegerProperty getPosPointerY()
    {
        return this.posPointerY;
    }

    /**
     * Regarde si il y a une graine sur la case.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public boolean haveSeeds(int x, int y)
    {
        return this.seedsArray[x][y] > 0;
    }

    /**
     * Regarde si il y a un mur sur la case.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public boolean haveWall(int x, int y)
    {
        return this.wallsArray[x][y];
    }

    /**
     * Regarde si il y a une fourmi sur la case.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public boolean haveAnt(int x, int y)
    {
        return this.antsArray[x][y] == 1 || this.antsArray[x][y] == 2;
    }

    /**
     * Si il y a une graine sur la case alors on l'enleve.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public void unsetSeeds(int x, int y)
    {
        if(haveSeeds(x, y))
        {
            this.seedsArray[x][y] = 0;
        }
    }

    /**
     * Si il y a un mur sur la case alors on l'enleve.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public void unsetWall(int x, int y)
    {
        if(haveWall(x, y))
        {
            this.wallsArray[x][y] = false;
        }
    }

    /**
     * Si il y a une fourmi sur la case alors on l'enleve.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public void unsetAnt(int x, int y)
    {
        if(haveAnt(x, y))
        {
            this.antsArray[x][y] = 0;
        }
    }

    /**
     * Ajoute le nombre "nbSeeds" au nombre de graines de la case (de 0 à qMax - 1).
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     * @param nbSeeds : nombre de graines à ajouté à la case.
     */
    public void setSeeds(int x, int y, int nbSeeds)
    {
        unsetWall(x, y);
        if(this.seedsArray[x][y] + nbSeeds >= 0)
        {
            if(this.seedsArray[x][y] + nbSeeds < this.qMax)
            {
                this.seedsArray[x][y] += nbSeeds;
            }
            else
            {
                this.seedsArray[x][y] = this.qMax - 1;
            }
        }
    }

    /**
     * Ajoute un mur sur la case et supprime potentiellement la fourmi et les graines.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public void setWall(int x, int y)
    {
        if(!haveWall(x, y))
        {
            unsetAnt(x, y);
            unsetSeeds(x, y);
            this.wallsArray[x][y] = true;
        }
    }

    /**
     * Ajoute une fourmi sur la case et supprime potentiellement le mur.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     */
    public void setAnt(int x, int y)
    {
        if(!haveAnt(x, y))
        {
            unsetWall(x, y);
            this.antsArray[x][y] = 1;
        }
    }

    /**
     * Affiche le plateau avec les graines, murs et fourmis.
     */
    public void allDraw()
    {
        this.allDraw(this.seedsArray, this.qMax, this.wallsArray, this.antsArray);
    }

    /**
     * Permet de modifier le plateau en fonction de :
     *          - isCurrentlyAddAnt : on ajoute une fourmi sur la case
     *          - isCurrentlyRemoveAnt : on supprime une fourmi sur la case
     *          - isCurrentlyAddWall : on ajoute un mur sur la case
     *          - isCurrentlyRemoveWall : on supprime un mur sur la case
     *   et on reaffiche le plateau après les modifications.
     *
     * @param x : la position x de la case.
     * @param y : la position y de la case.
     * @param me : mouseEvent qui nous permet de savoir si le click gauche ou shift + click gauche est en action.
     */
    public void drawOnGrid(int x, int y, MouseEvent me)
    {
        if(me.isPrimaryButtonDown())
        {
            if(me.isShiftDown())
            {
                // Ajout de fourmis.
                if(this.isCurrentlyAddAnt)
                {
                    setAnt(x, y);
                }
                if(this.isCurrentlyRemoveAnt)
                {
                    unsetAnt(x, y);
                }
            }
            else
            {
                // Ajout de murs.
                if(this.isCurrentlyAddWall)
                {
                    setWall(x, y);
                }
                if(this.isCurrentlyRemoveWall)
                {
                    unsetWall(x, y);
                }
            }
            allDraw();
        }
    }
}
