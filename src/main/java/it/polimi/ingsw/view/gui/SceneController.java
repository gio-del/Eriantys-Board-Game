package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.scene.BasicSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController extends ClientObservable {
    private static Scene actualScene;
    private static BasicSceneController actualController;

    public SceneController(Scene actualScene, BasicSceneController actualController) {
        SceneController.actualScene = actualScene;
        SceneController.actualController = actualController;
    }

    /**
     * This method change the actual scene
     *
     * @param actual is the ctual scene
     * @param newFXML is the fxml file reference to the new scene
     */
    public static void changeScene(Scene actual, String newFXML) {
        Parent root = null;
        try {
            root = FXMLLoader.load(SceneController.class.getResource(newFXML));
        } catch (IOException e) {
            //avvisare client prima
            System.exit(1);
        }
        Stage stage = (Stage) actual.getWindow();
        stage.setScene(new Scene(root,1220d,700d));
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