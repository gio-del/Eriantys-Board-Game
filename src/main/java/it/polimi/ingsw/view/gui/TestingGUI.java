package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.gui.scene.BoardSceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestingGUI extends Application {
    Game game;
    ShortModel resource;
    BoardSceneController boardSceneController;
    Parent root;
    Scene scene;
    Stage stage;

    @Override
    public void start(Stage stage) {

        game = new Game();
        game.addPlayer("Marco", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Giovanni", Wizard.SORCERER, TowerColor.WHITE);
        game.startGame(true);
        game.fillClouds();
        game.getBoard().getIslands().get(0).add(new Pawns(1, 0, 0, 1, 0));
        game.getBoard().getIslands().get(0).upgradeDimension(2);
        game.getBoard().getIslands().get(0).addTower(TowerColor.WHITE);

        game.getBoard().getIslands().get(1).add(new Pawns(0, 1, 1, 0, 1));
        game.getBoard().getIslands().get(1).addTower(TowerColor.GREY);
        game.getBoard().getIslands().get(1).upgradeBanTiles(1);
        game.getBoard().moveMotherNature(1);
        game.getBoard().getIslands().get(2).add(new Pawns(1, 1, 1, 1, 1));
        resource = new ShortModel(game, true);

        boardSceneController = new BoardSceneController(resource, "Giovanni");
        root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/board.fxml"));
            fxmlLoader.setController(boardSceneController);
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.stage = stage;
        scene = new Scene(Objects.requireNonNull(root));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/home.jpg"))));
        stage.setTitle("Testing GUI");
        stage.show();
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                method();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

    private void method() {
        game.getBoard().moveMotherNature(1);
        resource = new ShortModel(game, true);
        Platform.runLater(() -> {
            boardSceneController = new BoardSceneController(resource, "Giovanni");
            root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("/fxml/board.fxml"));
                fxmlLoader.setController(boardSceneController);
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            stage.setScene(new Scene(Objects.requireNonNull(root)));
        });
    }
}
