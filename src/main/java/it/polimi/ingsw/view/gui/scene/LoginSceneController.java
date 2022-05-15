package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginSceneController extends ClientObservable implements BasicSceneController {

    @FXML
    private TextField nicknameTextField;
    @FXML
    private Button confirmNicknameButton;

    public void confirm(MouseEvent mouseEvent) {
        nicknameTextField.setDisable(true);
        confirmNicknameButton.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.updateNickname(nicknameTextField.getText()))).start();
    }
}
