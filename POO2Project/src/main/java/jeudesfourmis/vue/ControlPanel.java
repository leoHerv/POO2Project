package jeudesfourmis.vue;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox
{
    public ControlPanel()
    {
        super();

        // On crée le bouton pour la loupe.
        Button btnMagnifier = new Button("Loupe");

        // On crée le bouton pour Pause / Play
        Button btnPlayPause = new Button("Play / Pause");

        // On crée le label pour l'affichage des tours de la simulation.
        Label labLaps = new Label("Nombres de tours de la simulation: 0");

        // On crée les trois sliders pour modifier la taille du plateau, qMax et a vitesse.
        Label labSize = new Label("Changer la taille");
        TextField textFieldSize = new TextField("50");
        Button btnChangeSize = new Button("Confirmer la nouvelle taille");
        SimpleBooleanProperty changeSizeActionProperty = new SimpleBooleanProperty(false);
        btnChangeSize.setOnAction(event ->
        {
            ConfirmationAlert sizeConfirmation = new ConfirmationAlert("Taille",
                    "Voulez vous changer la taille du plateau ?", changeSizeActionProperty);
        });

        HBox boxChangeSize = new HBox(textFieldSize, btnChangeSize);

        Label labQMax = new Label("Changer le nombre maximal de graines");
        Slider sliderQMax = new Slider();
        Label labSpeed = new Label("Changer la vitesse de la simulation");
        Slider sliderSpeed = new Slider();

        VBox boxModif = new VBox(labSize, boxChangeSize, labQMax, sliderQMax, labSpeed, sliderSpeed);

        // On crée le bouton de reset.
        Button btnReset = new Button("Reset");
        SimpleBooleanProperty resetActionProperty = new SimpleBooleanProperty(false);
        btnReset.setOnAction(event ->
        {
            ConfirmationAlert resetConfirmation = new ConfirmationAlert("Reset",
                    "Voulez vous reset le plateau ?", resetActionProperty);
        });

        // On crée les sliders de probabilité pour les graines, fourmis et les murs.
        Label labProba = new Label("Changer les probabilités d'apparitions");
        Label labSeeds = new Label("Pour les graines");
        Slider sliderSeeds = new Slider();
        Label labAnts = new Label("Pour les fourmis");
        Slider sliderAnts = new Slider();
        Label labWalls = new Label("Pour les murs");
        Slider sliderWalls = new Slider();

        VBox boxProba = new VBox(labProba, labSeeds, sliderSeeds, labAnts, sliderAnts, labWalls, sliderWalls);

        this.getChildren().addAll(btnMagnifier, btnPlayPause, labLaps, boxModif, btnReset, boxProba);
        this.setStyle("-fx-spacing: 30px;");
    }

}
