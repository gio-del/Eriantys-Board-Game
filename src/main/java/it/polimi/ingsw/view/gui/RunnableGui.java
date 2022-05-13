package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunnableGui extends Application {

    @Override
    public void start(Stage stage) {
        Gui view = new Gui();
        ClientController clientController = new ClientController(view);
        view.addObserver(clientController);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            //avvisare client prima
            System.exit(1);
        }

        LoginSceneController controller = loader.getController();
        controller.addObserver(clientController);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1280d);
        stage.setHeight(720d);
        stage.setResizable(true);
        stage.setTitle("Eriantys - by Giovanni De Lucia, Lorenzo Battiston, Lorenzo Dell'Era");
        stage.show();
    }
}