package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.scene.BasicSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController extends ClientObservable {
    private static Stage stage;
    private static Scene actualScene;
    private static BasicSceneController actualController;

    public SceneController(Stage primaryStage, Scene actualScene, BasicSceneController actualController) {
        this.stage = primaryStage;
        this.actualScene = actualScene;
        this.actualController = actualController;
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

    public static void changeScene(Scene newScene) {
        stage.setScene(newScene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(true);
        stage.setTitle("Eriantys - by Giovanni De Lucia, Lorenzo Battiston, Lorenzo Dell'Era");
        stage.show();
    }

}
