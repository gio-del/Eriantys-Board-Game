package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Button okButton;

    private Set<Assistant> playableAssistant;

    private Assistant selectedAssistant;

    @FXML
    private void initialize() {
        turtle.setDisable(!playableAssistant.contains(Assistant.TURTLE));
        elephant.setDisable(!playableAssistant.contains(Assistant.ELEPHANT));
        dog.setDisable(!playableAssistant.contains(Assistant.DOG));
        octopus.setDisable(!playableAssistant.contains(Assistant.OCTOPUS));
        crocodile.setDisable(!playableAssistant.contains(Assistant.CROCODILE));
        fox.setDisable(!playableAssistant.contains(Assistant.FOX));
        eagle.setDisable(!playableAssistant.contains(Assistant.EAGLE));
        cat.setDisable(!playableAssistant.contains(Assistant.CAT));
        ostrich.setDisable(!playableAssistant.contains(Assistant.OSTRICH));
        lion.setDisable(!playableAssistant.contains(Assistant.LION));

        turtle.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.TURTLE));
        elephant.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.ELEPHANT));
        dog.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.DOG));
        octopus.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.OCTOPUS));
        crocodile.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.CROCODILE));
        fox.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.FOX));
        eagle.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.EAGLE));
        cat.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.CAT));
        ostrich.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.OSTRICH));
        lion.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> selectAssistant(Assistant.LION));

        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> confirm());
    }

    private void disableAll() {
        turtle.setDisable(true);
        elephant.setDisable(true);
        dog.setDisable(true);
        octopus.setDisable(true);
        crocodile.setDisable(true);
        fox.setDisable(true);
        eagle.setDisable(true);
        cat.setDisable(true);
        ostrich.setDisable(true);
        lion.setDisable(true);
        okButton.setDisable(true);
    }
    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }

    private void selectAssistant(Assistant assistant) {
        this.selectedAssistant = assistant;
    }

    private void confirm() {
        disableAll();
        new Thread(() -> notifyObserver(obs -> obs.updateAssistant(selectedAssistant))).start();
    }
}