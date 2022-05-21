package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CharactersSceneController extends ClientObservable implements BasicSceneController {

    @FXML
    private Button okButton;

    @FXML
    private void initialize() {
        okButton.setCursor(Cursor.HAND);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> confirm());
    }

    public void confirm() {
        //TODO
    }
}