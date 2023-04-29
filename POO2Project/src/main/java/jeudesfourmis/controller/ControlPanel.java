package jeudesfourmis.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jeudesfourmis.model.FourmiliereModif;
import jeudesfourmis.vue.ConfirmationAlert;
import jeudesfourmis.vue.CustomSlider;
import jeudesfourmis.vue.StringNbLapsProperty;
import jeudesfourmis.vue.board.BoardWithZoomBoard;

public class ControlPanel extends VBox
{

    protected FourmiliereModif fourmiliere;

    public ControlPanel(SimpleBooleanProperty showZoomProperty, SimpleBooleanProperty displayGridProperty,
                        SimpleBooleanProperty boardEditable, FourmiliereModif fourmiliere, BoardWithZoomBoard board,
                                SimpleIntegerProperty nbLapsProperty)
    {
        super();
        this.fourmiliere = fourmiliere;
        //============================================================================================================//
        // On crée le bouton pour la loupe.
        //============================================================================================================//
        Button btnMagnifier = new Button("Loupe");
        btnMagnifier.setOnAction(event ->
        {
            showZoomProperty.setValue(!showZoomProperty.getValue());
        });

        //============================================================================================================//
        // On crée le label pour l'affichage des tours de la simulation.
        //============================================================================================================//
        StringNbLapsProperty stringNbLapsProperty = new StringNbLapsProperty(nbLapsProperty);
        Label labLaps = new Label("Nombre de tours de la simulation: 0");
        labLaps.textProperty().bind(stringNbLapsProperty);

        //============================================================================================================//
        // On crée les trois sliders pour modifier la taille du plateau, qMax et de la vitesse de la simulation.
        //============================================================================================================//

        // Pour la modification de la vitesse
        Label labSpeed = new Label("Changer la vitesse de la simulation");
        CustomSlider sliderSpeed = new CustomSlider("", 1, 100, 25);
        SimpleIntegerProperty speedProperty = sliderSpeed.getValueProperty();

        VBox boxSpeed = new VBox(labSpeed, sliderSpeed);

        // Pour la modification de la taille du plateau
        Label labSize = new Label("Changer la taille du plateau");
        CustomSlider sliderSize = new CustomSlider("", 20, 80, 20);
        SimpleIntegerProperty sizeProperty = sliderSize.getValueProperty();
        Button btnChangeSize = new Button("Confirmer la nouvelle taille");
        SimpleBooleanProperty changeSizeActionProperty = new SimpleBooleanProperty(false);
        btnChangeSize.setOnAction(event ->
        {
            ConfirmationAlert sizeConfirmation = new ConfirmationAlert("Taille",
                    "Voulez vous changer la taille du plateau ?", changeSizeActionProperty);
            UpdateBoardAndAnthill.changeSizeBoardAndAnthill(sizeProperty.getValue(), fourmiliere, board, changeSizeActionProperty);
        });

        HBox boxChangeSize = new HBox(sliderSize, btnChangeSize);

        // Pour la modification du nombre maximal de graines dans une case.
        Label labQMax = new Label("Changer le nombre maximal de graines");
        CustomSlider sliderQMax = new CustomSlider("", 10, 100, 20);
        SimpleIntegerProperty qMaxProperty = sliderQMax.getValueProperty();
        Button btnChangeQMax = new Button("Ok");
        btnChangeQMax.setOnAction(event ->
        {
            UpdateBoardAndAnthill.changeQMaxBoardAndAnthill(qMaxProperty.getValue(), fourmiliere, board);
        });

        HBox boxChangeQMax = new HBox(sliderQMax, btnChangeQMax);

        VBox boxModif = new VBox(labSize, boxChangeSize, labQMax, boxChangeQMax);

        //============================================================================================================//
        // On crée le bouton de reset.
        //============================================================================================================//
        Button btnReset = new Button("Reset");
        SimpleBooleanProperty resetActionProperty = new SimpleBooleanProperty(false);
        btnReset.setOnAction(event ->
        {
            ConfirmationAlert resetConfirmation = new ConfirmationAlert("Reset",
                    "Voulez vous reset le plateau ?", resetActionProperty);
            UpdateBoardAndAnthill.resetBoardAndAnthill(fourmiliere, board, resetActionProperty);
            nbLapsProperty.setValue(0);
        });

        //============================================================================================================//
        // On crée les sliders de probabilité pour les graines, fourmis et les murs.
        //============================================================================================================//
        Label labProba = new Label("Changer les probabilites d'apparitions");
        Label labSeeds = new Label("Pour les graines");
        CustomSlider sliderSeeds = new CustomSlider("", 0, 100, 25);
        Label labWalls = new Label("Pour les murs");
        CustomSlider sliderWalls = new CustomSlider("", 0, 100, 25);
        Label labAnts = new Label("Pour les fourmis");
        CustomSlider sliderAnts = new CustomSlider("", 0, 100, 25);
        Label labEmpty = new Label("Pour les cases vides");
        CustomSlider sliderEmpty = new CustomSlider("", 0, 100, 25);

        Button btnRandomMap = new Button("Plateau aleatoire");
        SimpleBooleanProperty randomMapActionProperty = new SimpleBooleanProperty(false);
        btnRandomMap.setOnAction(event ->
        {
            ConfirmationAlert randomMapConfirmation = new ConfirmationAlert("Plateau aleatoire",
                    "Voulez vous randomiser le plateau ?", randomMapActionProperty);
            UpdateBoardAndAnthill.getRandomMap(fourmiliere, board, sliderSeeds.getValueProperty(), sliderWalls.getValueProperty(),
                    sliderAnts.getValueProperty(), randomMapActionProperty, sliderEmpty.getValueProperty());
            nbLapsProperty.setValue(0);
        });

        VBox boxProba = new VBox(labProba, labSeeds, sliderSeeds, labWalls, sliderWalls, labAnts, sliderAnts, labEmpty, sliderEmpty, btnRandomMap);

        //============================================================================================================//
        // On crée le bouton pour Pause / Play
        //============================================================================================================//
        VBox boxToDisableInPlayMode = new VBox(boxModif, btnReset, boxProba);
        boxToDisableInPlayMode.setStyle("-fx-spacing: 30px;");

        ButtonEvolutionRun btnPlayPause = new ButtonEvolutionRun(fourmiliere, board, displayGridProperty,
                boardEditable, nbLapsProperty, speedProperty, boxToDisableInPlayMode);


        this.getChildren().addAll(btnMagnifier, btnPlayPause, labLaps, boxSpeed, boxToDisableInPlayMode);
        this.setStyle("-fx-spacing: 30px;");
    }

}
