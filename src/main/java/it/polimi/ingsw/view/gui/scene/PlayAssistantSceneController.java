package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.observer.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static it.polimi.ingsw.model.player.Assistant.*;

public class PlayAssistantSceneController extends ClientObservable implements BasicSceneController {
    private Assistant selected;
    private ShortModel resource;

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
    private void initialize() {
        ColorAdjust filter = new ColorAdjust();
        filter.setSaturation(-1d);
        if (!resource.getPlayableAssistant().contains(TURTLE)) {
            turtle.setEffect(filter);
            turtle.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(ELEPHANT)) {
            elephant.setEffect(filter);
            elephant.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(DOG)) {
            dog.setEffect(filter);
            dog.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(OCTOPUS)) {
            octopus.setEffect(filter);
            octopus.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(CROCODILE)) {
            crocodile.setEffect(filter);
            crocodile.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(FOX)) {
            fox.setEffect(filter);
            fox.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(EAGLE)) {
            eagle.setEffect(filter);
            eagle.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(CAT)) {
            cat.setEffect(filter);
            cat.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(OSTRICH)) {
            ostrich.setEffect(filter);
            ostrich.setDisable(true);
        }
        if (!resource.getPlayableAssistant().contains(LION)) {
            lion.setEffect(filter);
            lion.setDisable(true);
        }
    }

    @FXML
    public void play(MouseEvent event) {
        turtle.setDisable(false);
        octopus.setDisable(false);
        dog.setDisable(false);
        octopus.setDisable(false);
        crocodile.setDisable(false);
        fox.setDisable(false);
        eagle.setDisable(false);
        cat.setDisable(false);
        ostrich.setDisable(false);
        lion.setDisable(false);
        ImageView img = (ImageView) event.getTarget();
        if (img==turtle) {
            selected = TURTLE;
        }
        if (img==elephant) {
            selected = ELEPHANT;
        }
        if (img==dog) {
            selected = DOG;
        }
        if (img==octopus) {
            selected = OCTOPUS;
        }
        if (img==crocodile) {
            selected = CROCODILE;
        }
        if (img==fox) {
            selected = FOX;
        }
        if (img==eagle) {
            selected = EAGLE;
        }
        if (img==cat) {
            selected = CAT;
        }
        if (img==ostrich) {
            selected = OSTRICH;
        }
        if (img==lion) {
            selected = LION;
        }
        //new Thread(() -> notifyObserver(obs -> obs.updateAssistant(selected).start();
        //chiudere finestra aperta precedentemente
    }
}