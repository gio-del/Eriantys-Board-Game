package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.observer.ClientObserver;
import it.polimi.ingsw.view.gui.scene.BasicSceneController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.List;

/**
 * This class implements a generic scene controller
 */
public class SceneManager {
    private static Scene actualScene;
    private static BasicSceneController actualController;

    private SceneManager() {
    }

    /**
     * This method changes the current scene
     *
     * @param observerList list of the observers
     * @param newScene     the new scene
     * @param pathToFxml   the fxml file's name of the new scene
     */
    public static void changeScene(List<ClientObserver> observerList, Scene newScene, String pathToFxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneManager.class.getResource("/fxml/" + pathToFxml));
        Parent root;
        try {
            root = loader.load();
            actualController = loader.getController();
            ClientObservable observable = (ClientObservable) actualController;
            observerList.forEach(observable::addObserver);

            actualScene = newScene;
            actualScene.setRoot(root);
            actualScene.getWindow().sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method changes the current scene
     *
     * @param observers  list of the observers
     * @param pathToFXML the fxml file's name of the new scene
     */
    public static void changeScene(List<ClientObserver> observers, String pathToFXML) {
        changeScene(observers, actualScene, pathToFXML);
    }

    /**
     * This method changes the current scene
     *
     * @param controller new controller
     * @param pathToFXML the fxml file's name of the new scene
     */
    public static void changeScene(BasicSceneController controller, String pathToFXML) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + pathToFXML));
            fxmlLoader.setController(controller);
            actualController = controller;
            Parent parent = fxmlLoader.load();
            actualScene.setRoot(parent);
            Platform.runLater(() -> actualScene.getWindow().sizeToScene());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method shows a notification
     *
     * @param type    of the notification
     * @param message the text of the message
     */
    public static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Returns the actual scene
     *
     * @return actual scene
     */
    public static Scene getActualScene() {
        return actualScene;
    }

    /**
     * Returns the actual controller
     *
     * @return actual controller
     */
    public static BasicSceneController getActualController() {
        return actualController;
    }

}