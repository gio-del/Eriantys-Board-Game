package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Set;

public class PlayAssistantSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private ImageView turtle;
    @FXML
    private ImageView elephant;
    @FXML
    private ImageView dog;
    @FXML
    private ImageView octopus;
    @FXML
    private ImageView crocodile;
    @FXML
    private ImageView fox;
    @FXML
    private ImageView eagle;
    @FXML
    private ImageView cat;
    @FXML
    private ImageView ostrich;
    @FXML
    private ImageView lion;

    private Set<Assistant> playableAssistant;

    @FXML
    private void initialize() {
        lion.setDisable(!playableAssistant.contains(Assistant.LION));
        lion.setDisable(!playableAssistant.contains(Assistant.LION));
        lion.setDisable(!playableAssistant.contains(Assistant.LION));
    }

    private void disableAll() {
        turtle.setDisable(false);
        elephant.setDisable(false);
        dog.setDisable(false);
        octopus.setDisable(false);
        crocodile.setDisable(false);
        fox.setDisable(false);
        eagle.setDisable(false);
        cat.setDisable(false);
        ostrich.setDisable(false);
        lion.setDisable(false);
    }
    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }


}