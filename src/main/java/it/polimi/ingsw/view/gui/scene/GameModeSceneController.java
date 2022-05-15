package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class GameModeSceneController extends ClientObservable implements BasicSceneController {


    @FXML
    private ChoiceBox<String> numOfPlayerChoiceBox;
    @FXML
    private Button confirmGameModeButton;
    @FXML
    private CheckBox expertMode;

    @FXML
    private void initialize() {
        numOfPlayerChoiceBox.setValue("2");
        numOfPlayerChoiceBox.setItems(FXCollections.observableArrayList("2", "3"));
    }

    public void confirm(MouseEvent mouseEvent) {
        expertMode.setDisable(true);
        confirmGameModeButton.setDisable(true);
        numOfPlayerChoiceBox.setDisable(true);

        String mode = "Simple";
        if (expertMode.isSelected()) {
            mode = "Expert";
        }

        String finalMode = mode;
        new Thread(() -> notifyObserver(obs -> obs.updateGameModeNumPlayer(finalMode, Integer.parseInt(numOfPlayerChoiceBox.getValue())))).start();

    }
}
