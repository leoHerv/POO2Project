package jeuDesFourmis.vue;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmationAlert extends Alert
{
    public ConfirmationAlert(String title, String header, SimpleBooleanProperty responseProperty)
    {
        super(AlertType.CONFIRMATION);
        this.setTitle(title);
        this.setHeaderText(header);
        //this.setContentText("");

        Optional<ButtonType> result = this.showAndWait();

        // Si on appuie sur "ok" alors on fait l'action du bouton.
        responseProperty.setValue(result.isPresent() && result.get() == ButtonType.OK);

    }
}
