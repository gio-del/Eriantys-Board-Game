package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.scene.BasicSceneController;
import javafx.scene.Scene;

public class SceneController extends ClientObservable {
    private static Scene actualScene;
    private static BasicSceneController actualController;

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
