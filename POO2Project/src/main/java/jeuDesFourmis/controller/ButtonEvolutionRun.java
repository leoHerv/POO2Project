package jeuDesFourmis.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import jeuDesFourmis.model.FourmiliereModif;
import jeuDesFourmis.vue.board.BoardWithZoomBoard;

import java.util.Objects;

public class ButtonEvolutionRun extends Button
{
    protected Service<Void> service;

    protected FourmiliereModif fourmiliere;
    protected BoardWithZoomBoard board;
    protected SimpleBooleanProperty displayGridProperty;
    protected SimpleBooleanProperty boardEditable;
    protected SimpleIntegerProperty nbLapsProperty;
    protected SimpleIntegerProperty speedProperty;
    protected int speed;

    protected VBox boxToDisableInPlayMode;

    public ButtonEvolutionRun(FourmiliereModif fourmiliere, BoardWithZoomBoard board,
                              SimpleBooleanProperty displayGridProperty, SimpleBooleanProperty boardEditable,
                              SimpleIntegerProperty nbLapsProperty, SimpleIntegerProperty speedProperty,
                              VBox boxToDisableInPlayMode)
    {
        super("Play");
        this.fourmiliere = fourmiliere;
        this.board = board;
        this.displayGridProperty = displayGridProperty;
        this.boardEditable = boardEditable;
        this.nbLapsProperty = nbLapsProperty;
        this.speedProperty = speedProperty;
        this.speed = 1;
        this.boxToDisableInPlayMode = boxToDisableInPlayMode;
        this.setTache();
        this.setAction();
    }

    private void setTache()
    {
        Runnable updateBoard = new Runnable()
        {
            @Override
            public void run()
            {
                board.allDraw();
                nbLapsProperty.setValue(nbLapsProperty.getValue() + 1);
            }
        };

        Runnable checkIsSpeedChange = new Runnable()
        {
            @Override
            public void run()
            {
                speed = speedProperty.getValue();
            }
        };

        this.service = new Service<Void>()
        {
            @Override
            protected Task<Void> createTask()
            {
                return new Task<Void>()
                {
                    @Override
                    protected Void call() throws InterruptedException //throws Exception
                    {
                        while(!boardEditable.getValue())
                        {
                            Platform.runLater(checkIsSpeedChange);
                            fourmiliere.evolue();

                            board.setSeedsArray(fourmiliere.getSeedsArray());
                            board.setWallsArray(fourmiliere.getWallsArray());
                            board.setAntsArray(fourmiliere.getAntsArray());

                            Platform.runLater(updateBoard);
                            Platform.runLater(checkIsSpeedChange);
                            Thread.sleep(1000L / speed);
                        }
                        return null;
                    }
                    /*
                    @Override
                    protected void cancelled()
                    {
                        Platform.runLater(updateButtonCan);
                    }

                    @Override
                    protected void succeeded()
                    {
                        Platform.runLater(updateButtonSucc);
                    }*/
                };
            }
        };
    }

    private void setAction()
    {
        this.setOnAction(event ->
        {
            if(Objects.equals(this.getText(), "Play"))
            {
                this.displayGridProperty.setValue(false);
                this.boardEditable.setValue(false);
                this.setText("Pause");
                this.boxToDisableInPlayMode.setDisable(true);
                // On prepare la simulation.
                this.service.stateProperty().addListener((ObservableValue, oldValue, newValue) ->
                {
                    switch(newValue)
                    {
                        case FAILED:
                        case CANCELLED:
                        case SUCCEEDED:
                            service.reset();
                            break;
                    }
                });

                // On applique les modifications que l'on a faite à la fourmiliere.
                fourmiliere.setSeedsArray(board.getSeedsArray());
                fourmiliere.setWallsArray(board.getWallsArray());
                fourmiliere.setAntsArray(board.getAntsArray());

                // On lance la simulation.
                this.service.start();
            }
            else
            {
                this.service.cancel();
                this.displayGridProperty.setValue(true);
                this.boardEditable.setValue(true);
                this.setText("Play");
                this.boxToDisableInPlayMode.setDisable(false);
            }

        });
    }
}
