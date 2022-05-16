package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.observer.ClientObserver;
import it.polimi.ingsw.view.gui.scene.BasicSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.List;

public class SceneController extends ClientObservable {
    private static Scene actualScene;
    private static BasicSceneController actualController;

    public static void changeScene(List<ClientObserver> observerList, Scene newScene, String pathToFxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/" + pathToFxml));
        Parent root;
        try {
            root = loader.load();

            actualController = loader.getController();
            ClientObservable observable = (ClientObservable) actualController;
            observerList.forEach(observable::addObserver);

            actualScene = newScene;
            actualScene.setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //System.exit(1);
        }

    }

    public static void changeScene(List<ClientObserver> observers, String pathToFXML) {
        changeScene(observers, actualScene, pathToFXML);
    }

    public static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Returns the actual scene
     *
     * @return actual scene
     */
    public Scene getActualScene() {
        return actualScene;
    }

    /**
     * Returns the actual controller
     *
     * @return actual controller
     */
    public BasicSceneController getActualController() {
        return actualController;
    }

}