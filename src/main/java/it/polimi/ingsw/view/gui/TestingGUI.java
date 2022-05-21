package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.gui.scene.BoardSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public class TestingGUI extends Application {
    @Override
    public void start(Stage stage) {
        Gui view = new Gui();
        ClientController clientController = new ClientController(view);
        view.addObserver(clientController);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/board.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            exit(1);
        }

        BoardSceneController boardSceneController = loader.getController();
        boardSceneController.addObserver(clientController);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/home.jpg"))));
        stage.setTitle("Testing GUI");
        stage.show();
    }
}
