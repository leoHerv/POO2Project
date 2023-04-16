package jeudesfourmis.vue.tests.grid;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class CustomLine extends Line
{
    public CustomLine(int xStart, int yStart, int xEnd, int yEnd, String style, Pane pane)
    {
        super(xStart, yStart, xEnd, yEnd);
        this.setStyle(style);
        pane.getChildren().add(this);
    }
}
