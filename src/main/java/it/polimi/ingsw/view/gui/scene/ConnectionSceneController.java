package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * This class implements the controller of the connection scene
 */
public class ConnectionSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private TextField portField;
    @FXML
    private Button button;
    @FXML
    private TextField ipField;

    /**
     * Complete the server-client connection
     */
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
            SceneManager.showAlert(Alert.AlertType.ERROR, "IP address or port number not correct!");
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