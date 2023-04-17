package jeudesfourmis.vue.tests.grid;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class StringGridSizeProterty extends StringBinding
{
    private final SimpleIntegerProperty widthProperty;
    private final SimpleIntegerProperty heightProperty;
    private final String style;
    private final SimpleIntegerProperty boxSizeProperty;

    public StringGridSizeProterty(SimpleIntegerProperty widthProperty, SimpleIntegerProperty heightProperty,
                                  String style, SimpleIntegerProperty boxSizeProperty)
    {
        super();
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
        this.style = style;
        this.boxSizeProperty = boxSizeProperty;
        bind(this.widthProperty, this.heightProperty, this.boxSizeProperty);
    }

    @Override
    protected String computeValue()
    {
        int sizeBox = boxSizeProperty.getValue();
        int width = widthProperty.getValue() * sizeBox;
        int height = heightProperty.getValue() * sizeBox;

        return  "-fx-min-width: " + width + "px;" +
                "-fx-min-height: " + height + "px;" +
                "-fx-max-width: " + width + "px;" +
                "-fx-max-height: " + height + "px;" +
                style;
    }
}
