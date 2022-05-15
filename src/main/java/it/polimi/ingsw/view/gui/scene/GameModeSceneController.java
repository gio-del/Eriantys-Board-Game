package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;

public class GameModeSceneController extends ClientObservable implements BasicSceneController {
    //radio button per numero di player e check box per facile o difficle mode
    @FXML
    private RadioButton radio;

    @FXML
    private Slider slider;

    @FXML
    public void confirm(ActionEvent event) {
        //inoltre cambia scena alla game/board scene
    }
}
