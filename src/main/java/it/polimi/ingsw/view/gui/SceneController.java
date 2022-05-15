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

    /**
     * SceneController initialization
     *
     * @param firstScene is the first scene
     * @param firstController is the first controller
     */
    public SceneController(Scene firstScene, BasicSceneController firstController) {
        this.actualScene = firstScene;
        this.actualController = firstController;
    }

    /**
     * SceneController initialization
     *
     * @param newScene is the new scene
     * @param newController is the first controller
     */
    private static void setSceneController(Scene newScene, BasicSceneController newController) {
        actualScene = newScene;
        actualController = newController;
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

    /**
     * This method change the actual scene with another
     *
     * @param active is the actual scene
     * @param newFXML is the fxml file containing the new scene
     */
    public static void changeScene(Scene active, String newFXML) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SceneController.class.getResource(newFXML));
            root = loader.load();
        } catch (IOException e) {
            //avvisare client prima
            System.exit(1);
        }
        Stage stage = (Stage) active.getWindow();
        Scene scene = new Scene(root, 1200d, 700d);
        stage.setScene(scene);
    }
}