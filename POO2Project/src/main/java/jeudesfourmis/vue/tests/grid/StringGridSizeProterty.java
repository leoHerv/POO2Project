package jeudesfourmis.vue.tests.grid;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class StringGridSizeProterty extends StringBinding
{
    private final SimpleIntegerProperty widthProperty;
    private final SimpleIntegerProperty heightProperty;
    private final String style;
    private final int boxSize;

    public StringGridSizeProterty(SimpleIntegerProperty widthProperty, SimpleIntegerProperty heightProperty,
                                  String style, int boxSize)
    {
        super();
        this.widthProperty = widthProperty;
        this.heightProperty = heightProperty;
        this.style = style;
        this.boxSize = boxSize;
        bind(this.widthProperty, this.heightProperty);
    }

    @Override
    protected String computeValue()
    {
        int width = widthProperty.getValue() * boxSize;
        int height = heightProperty.getValue() * boxSize;

        return  "-fx-min-width: " + width + "px;" +
                "-fx-min-height: " + height + "px;" +
                "-fx-max-width: " + width + "px;" +
                "-fx-max-height: " + height + "px;" +
                style;
    }
}
