package jeudesfourmis.vue.tests.grid;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BoardWithZoomBoard extends GridEditable
{

    public BoardWithZoomBoard(boolean displayGrid, int gridSize, int[][] seedsArray, int qMax,
                              boolean[][] wallsArray, int[][] antsArray, SimpleBooleanProperty showZoomProperty)
    {
        super(displayGrid, gridSize, seedsArray, qMax, wallsArray, antsArray);

        // On crée un autre plateau qui affiche le plateau en plus gros.
        Grid boardZoom = new Grid(true, 11, 20);
        boardZoom.allDraw(this.seedsArray, this.qMax, this.wallsArray, this.antsArray, 0, 0, 11);

        BorderPane baseZoom = new BorderPane();
        baseZoom.setCenter(boardZoom);

        Scene sceneZoom = new Scene(baseZoom, 500, 400);

        Stage stageZoom = new Stage();
        stageZoom.setTitle("Zoom");
        stageZoom.setScene(sceneZoom);

        // Si on appuie sur le bouton loupe on montre le plateau.
        showZoomProperty.addListener(event ->
        {
            boolean show = showZoomProperty.getValue();
            if(show)
            {
                stageZoom.show();
            }
            else
            {
                stageZoom.close();
            }
        });

        // Si la valeur du curseur principale change alors on change l'affichage du plateau de zoom.
        BoardWithZoomBoard board = this;

        ChangeListener<Number> xyMove = new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                int posX = board.getPosPointerX().getValue();
                int posY = board.getPosPointerY().getValue();

                int boardSize = board.sizeGridProperty.getValue();

                // Pour ne pas afficher des cases dans le vide.
                if(posX < 6)
                {
                    posX = 5;
                }
                if(posY < 6)
                {
                    posY = 5;
                }
                if(posX > boardSize - 6)
                {
                    posX = boardSize - 6;
                }
                if(posY >= boardSize - 6)
                {
                    posY = boardSize - 6;
                }

                boardZoom.allDraw(board.seedsArray, board.qMax, board.wallsArray, board.antsArray, posX - 5, posY - 5, 11);
            }
        };

        board.getPosPointerX().addListener(xyMove);
        board.getPosPointerY().addListener(xyMove);
    }
}
