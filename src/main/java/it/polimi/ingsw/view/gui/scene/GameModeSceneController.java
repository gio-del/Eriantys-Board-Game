package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameModeSceneController extends ClientObservable implements BasicSceneController {


    @FXML
    private Group groupOfWizard;
    @FXML
    private ImageView loading;
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
        confirmGameModeButton.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                confirm();
            }
        });
    }

    public void confirm() {
        expertMode.setDisable(true);
        expertMode.setVisible(false);
        confirmGameModeButton.setDisable(true);
        confirmGameModeButton.setVisible(false);
        numOfPlayerChoiceBox.setDisable(true);
        numOfPlayerChoiceBox.setVisible(false);
        loading.setVisible(true);
        groupOfWizard.setVisible(true);

        String mode = "Simple";
        if (expertMode.isSelected()) {
            mode = "Expert";
        }

        String finalMode = mode;
        new Thread(() -> notifyObserver(obs -> obs.updateGameModeNumPlayer(finalMode, Integer.parseInt(numOfPlayerChoiceBox.getValue())))).start();
    }
}