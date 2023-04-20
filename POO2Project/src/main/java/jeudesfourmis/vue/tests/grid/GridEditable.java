package jeudesfourmis.vue.tests.grid;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GridEditable extends Grid
{
    protected SimpleBooleanProperty isEditableProperty;

    public GridEditable(boolean displayGrid, int gridSize)
    {
        super(displayGrid, gridSize);

        this.isEditableProperty = new SimpleBooleanProperty(true);

        this.setOnMouseDragged(event ->
        {

        });
        EventHandler<MouseEvent> handleClickAndDrag = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(isEditableProperty.getValue())
                {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    int caseX = x / sizeBoxProperty.getValue();
                    int caseY = y / sizeBoxProperty.getValue();

                    System.out.print(x + " " + y + " : " + caseX + " " + caseY + " = ");
                    if(event.isPrimaryButtonDown())
                    {
                        System.out.println("Click gauche");
                    }
                    else if(event.isSecondaryButtonDown())
                    {
                        if(event.isShiftDown())
                        {
                            System.out.println("Click shif + droit");
                        }
                        else
                        {
                            System.out.println("Click droit");
                        }
                    }
                }
            }
        };
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, handleClickAndDrag);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, handleClickAndDrag);
    }

    /**
     * Retourne isEditableProperty pour pouvoir modifier si la grille doit être modifiable ou non.
     */
    public SimpleBooleanProperty getIsEditableProperty()
    {
        return this.isEditableProperty;
    }
}
