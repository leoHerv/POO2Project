package jeudesfourmis.vue.tests.grid;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class StringGridSizeProperty extends StringBinding
{
    private final SimpleIntegerProperty sizeProperty;
    private final String style;
    private final SimpleIntegerProperty boxSizeProperty;

    public StringGridSizeProperty(SimpleIntegerProperty sizeProperty, String style, SimpleIntegerProperty boxSizeProperty)
    {
        super();
        this.sizeProperty = sizeProperty;
        this.style = style;
        this.boxSizeProperty = boxSizeProperty;
        bind(this.sizeProperty, this.boxSizeProperty);
    }

    @Override
    protected String computeValue()
    {
        int sizeBox = boxSizeProperty.getValue();
        int size = sizeProperty.getValue() * sizeBox;

        return  "-fx-min-width: " + size + "px;" +
                "-fx-min-height: " + size + "px;" +
                "-fx-max-width: " + size + "px;" +
                "-fx-max-height: " + size + "px;" +
                style;
    }
}
