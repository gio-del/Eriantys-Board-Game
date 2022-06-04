package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * This class implements the controller of the login scene
 */
public class LoginSceneController extends ClientObservable implements BasicSceneController {

    private final Gui gui;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Button confirmNicknameButton;
    @FXML
    private Group groupOfWizard;
    @FXML
    private ImageView loading;

    public LoginSceneController(Gui gui) {
        this.gui = gui;
    }

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            anchorPane.requestFocus();
            nicknameTextField.setPromptText("Insert Nickname");
        });
        confirmNicknameButton.setOnAction(actionEvent -> confirm());
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
                confirm();
        });
    }

    /**
     * Complete the login phase
     */
    public void confirm() {
        String nickname = nicknameTextField.getText();
        if (nickname.trim().equals("")) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "You must chose a nickname");
            return;
        }
        groupOfWizard.setVisible(true);
        loading.setVisible(true);
        nicknameTextField.setDisable(true);
        confirmNicknameButton.setDisable(true);

        gui.setNickname(nickname);
        new Thread(() -> notifyObserver(obs -> obs.updateNickname(nickname))).start();
    }
}