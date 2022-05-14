package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class HomeSceneController extends ClientObservable implements BasicSceneController {

    @FXML
    public void startLogin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/connect.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            //avvisare client prima
            System.exit(1);
        }
        Scene scene = new Scene(root);
        SceneController.changeScene(scene);
    }
}
