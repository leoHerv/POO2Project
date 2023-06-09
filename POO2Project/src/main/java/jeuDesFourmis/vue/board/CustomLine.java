package jeuDesFourmis.vue.board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class CustomLine extends Line
{
    public CustomLine(int xStart, int yStart, int xEnd, int yEnd, String style, Pane pane)
    {
        super(xStart, yStart, xEnd, yEnd);
        this.setStyle(style);
        this.setMouseTransparent(true);
        pane.getChildren().add(this);
    }
}
