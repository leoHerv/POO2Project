package jeudesfourmis.vue;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

public class CustomSlider extends HBox
{
    protected SimpleIntegerProperty valueProperty;
    public CustomSlider(String name, int min, int max, int nbGrad)
    {
        super();
        // On crée un Label.
        Label label = new Label(name);
        // On crée le TextField.
        CustomTextField field = new CustomTextField(min, max);
        // On crée le slider.
        Slider slider = new Slider(min, max, min);
        slider.valueProperty().addListener((obs, oldval, newVal) -> slider.setValue(newVal.intValue()));
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(nbGrad);
        slider.setBlockIncrement(1);

        this.valueProperty = new SimpleIntegerProperty(min);
        this.valueProperty.bind(slider.valueProperty());

        field.textProperty().bindBidirectional(slider.valueProperty(), new NumberStringConverter());

        this.getChildren().addAll(label, slider, field);
    }

    public SimpleIntegerProperty getValueProperty()
    {
        return this.valueProperty;
    }
}
