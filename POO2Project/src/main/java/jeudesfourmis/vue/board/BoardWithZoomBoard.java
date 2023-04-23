package jeudesfourmis.vue.board;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BoardWithZoomBoard extends BoardEditable
{

    public static final int ZOOM_SIZE_BOX = 30;
    public static final int ZOOM_SIZE_BOARD = 11;

    public BoardWithZoomBoard(boolean displayBoard, int boardSize, int[][] seedsArray, int qMax,
                              boolean[][] wallsArray, int[][] antsArray, SimpleBooleanProperty showZoomProperty,
                              SimpleIntegerProperty nbLapsProperty)
    {
        super(displayBoard, boardSize, seedsArray, qMax, wallsArray, antsArray);

        // On crée un autre plateau qui affiche le plateau en plus gros.
        Board boardZoom = new Board(true, ZOOM_SIZE_BOARD, ZOOM_SIZE_BOX);
        boardZoom.allDraw(this.seedsArray, this.qMax, this.wallsArray, this.antsArray, 0, 0, ZOOM_SIZE_BOARD);

        BorderPane baseZoom = new BorderPane();
        baseZoom.setCenter(boardZoom);

        Scene sceneZoom = new Scene(baseZoom, ZOOM_SIZE_BOARD * ZOOM_SIZE_BOX, ZOOM_SIZE_BOARD * ZOOM_SIZE_BOX);

        Stage stageZoom = new Stage();
        stageZoom.setTitle("Zoom");
        stageZoom.setScene(sceneZoom);
        stageZoom.setResizable(false);

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

                int boardSize = board.sizeBoardProperty.getValue();

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

                boardZoom.allDraw(board.seedsArray, board.qMax, board.wallsArray, board.antsArray, posX - 5, posY - 5, ZOOM_SIZE_BOARD);
            }
        };

        this.getPosPointerX().addListener(xyMove);
        this.getPosPointerY().addListener(xyMove);
        nbLapsProperty.addListener(xyMove);
    }
}
