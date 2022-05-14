package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class ConnectionController extends ClientObservable implements BasicSceneController {
    private String ipPort;

    @FXML
    private TextField nameField;

    @FXML
    public void confirm(ActionEvent event) {
        ipPort = nameField.getText();
    }
}

//serve scena inserire porta: png eryantis e inserire ip port