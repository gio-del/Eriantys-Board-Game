package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.gui.scene.BoardSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TestingGUI extends Application {
    @Override
    public void start(Stage stage) {

        Game game = new Game();
        game.addPlayer("Marco", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Giovanni", Wizard.SORCERER, TowerColor.WHITE);
        game.startGame(true);
        ShortModel resource = new ShortModel(game, true);

        BoardSceneController boardSceneController = new BoardSceneController(resource, "Giovanni");
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/board.fxml"));
            fxmlLoader.setController(boardSceneController);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        Scene scene = new Scene(Objects.requireNonNull(root));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/home.jpg"))));
        stage.setTitle("Testing GUI");
        stage.show();
    }
}
