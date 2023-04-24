package jeudesfourmis.model;

import java.util.LinkedList;
import java.util.List;

public class FourmiliereModif extends Fourmiliere
{
    protected int sizeBoard;

    /**
     * Crée une fourmiliere de largeur size et de hauteursize.
     *
     * @param sizeBoard : largeur et hauteur.
     * @param qMax : la qte max de graines par case
     */
    public FourmiliereModif(int sizeBoard, int qMax)
    {
        super(sizeBoard - 2, sizeBoard - 2, qMax);
        this.sizeBoard = sizeBoard;
    }

    public void setNewFourmiliere(FourmiliereModif f)
    {
        this.largeur = f.largeur;
        this.hauteur = f.hauteur;
        this.sizeBoard = f.sizeBoard;
        this.qMax = f.qMax;
        this.lesFourmis = f.lesFourmis;
        this.murs = f.murs;
        this.fourmis = f.fourmis;
        this.qteGraines = f.qteGraines;
    }

    public int[][] reverseArray(int[][] array)
    {
        int[][] newArray = new int[sizeBoard][sizeBoard];
        for(int i = 0; i < sizeBoard; i++)
        {
            for(int j = 0; j < sizeBoard; j++)
            {
                newArray[i][j] = array[j][i];
            }
        }
        return newArray;
    }

    public boolean[][] reverseArray(boolean[][] array)
    {
        boolean[][] newArray = new boolean[sizeBoard][sizeBoard];
        for(int i = 0; i < sizeBoard; i++)
        {
            for(int j = 0; j < sizeBoard; j++)
            {
                newArray[i][j] = array[j][i];
            }
        }
        return newArray;
    }

    public int[][] getSeedsArray()
    {
        return reverseArray(qteGraines);
    }

    public boolean[][] getWallsArray()
    {
        return reverseArray(murs);
    }

    public int[][] getAntsArray()
    {
        int[][] antsArray = new int[sizeBoard][sizeBoard];
        for(int i = 0; i < sizeBoard; i++)
        {
            for(int j = 0; j < sizeBoard; j++)
            {
                if(contientFourmi(i, j))
                {
                    Fourmi fourmi = null;
                    for(Fourmi f : lesFourmis)
                    {
                        if(f.getX() == i && f.getY() == j)
                        {
                            fourmi = f;
                            break;
                        }
                    }
                    if(fourmi == null)
                    {
                        antsArray[i][j] = 0;
                    }
                    else if(fourmi.porte())
                    {
                        antsArray[i][j] = 2;
                    }
                    else
                    {
                        antsArray[i][j] = 1;
                    }
                }
                else
                {
                    antsArray[i][j] = 0;
                }
            }
        }
        return antsArray;
    }

    public void setSeedsArray(int[][] seedsArray)
    {
        this.qteGraines = reverseArray(seedsArray);
    }

    public void setWallsArray(boolean[][] wallsArray)
    {
        this.murs = reverseArray(wallsArray);
    }

    public void setAntsArray(int[][] antsArray)
    {
        boolean[][] newAntsArray = new boolean[sizeBoard][sizeBoard];
        List<Fourmi> antsList = new LinkedList<>();

        for(int i = 0; i < sizeBoard; i++)
        {
            for(int j = 0; j < sizeBoard; j++)
            {
                if(antsArray[i][j] == 0)
                {
                    newAntsArray[j][i] = false;
                }
                else
                {
                    newAntsArray[j][i] = true;
                    antsList.add(new Fourmi(i, j, antsArray[i][j] == 2));
                }
            }
        }
        this.lesFourmis = antsList;
        this.fourmis = newAntsArray;
    }
}
