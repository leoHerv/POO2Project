package jeudesfourmis.vue;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class StringNbLapsProperty extends StringBinding
{
    protected SimpleIntegerProperty nbLapsProperty;
    public StringNbLapsProperty(SimpleIntegerProperty nbLapsProperty)
    {
        super();
        this.nbLapsProperty = nbLapsProperty;
        bind(this.nbLapsProperty);
    }

    @Override
    protected String computeValue()
    {
        int nbLaps = nbLapsProperty.getValue();
        return "Nombre de tours de la simulation: " + nbLaps;
    }
}
