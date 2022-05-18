package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ConnectionSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private TextField portField;
    @FXML
    private Button button;
    @FXML
    private TextField ipField;

    @FXML
    private void initialize() {
        button.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                confirm();
            }
        });
    }

    @FXML
    private void confirm() {
        String address = ipField.getText();
        String stringPort = portField.getText();
        int port;

        try {
            port = Integer.parseInt(stringPort);
        } catch (NumberFormatException e) {
            port = -1;
        }

        boolean valid = ClientController.isValidPort(port) && ClientController.isValidIp(address);

        if (!valid) {
            SceneController.showAlert(Alert.AlertType.ERROR, "IP address or port number not correct!");
        }

        if (valid) {
            ipField.setDisable(true);
            portField.setDisable(true);
            button.setDisable(true);

            int finalPort = port;
            new Thread(() -> notifyObserver(obs -> obs.updateConnection(address, finalPort))).start();
        }
    }
}