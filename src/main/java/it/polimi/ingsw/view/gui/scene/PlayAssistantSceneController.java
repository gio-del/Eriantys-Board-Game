package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the controller of the assistants choose scene
 */
public class PlayAssistantSceneController extends ClientObservable implements BasicSceneController {
    private final Map<Assistant, ImageView> assistantMapImage = new EnumMap<>(Assistant.class);
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

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        Arrays.stream(PlayAssistantSceneController.class.getDeclaredFields()).
                filter(
                        field -> Arrays.stream(Assistant.values()).
                                anyMatch(assistant -> assistant.name().equalsIgnoreCase(field.getName()))
                ).forEach(field -> {
                    try {
                        assistantMapImage.put(Assistant.valueOf(field.getName().toUpperCase()), (ImageView) field.get(this));
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                });

        for (Assistant assistant : Assistant.values()) {
            if (!playableAssistant.contains(assistant)) {
                disable(assistant);
            } else {
                assistantMapImage.get(assistant).setCursor(Cursor.HAND);
                assistantMapImage.get(assistant).addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> selectAssistant(assistant));
            }
        }

        okButton.setCursor(Cursor.HAND);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> confirm());
    }

    private void disable(Assistant assistant) {
        ColorAdjust effect = new ColorAdjust();
        effect.setSaturation(-1d);
        assistantMapImage.get(assistant).setEffect(effect);
    }

    private void disableAll() {
        for (Assistant assistant : Assistant.values())
            assistantMapImage.get(assistant).setDisable(true);
        okButton.setDisable(true);
    }

    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }

    private void selectAssistant(Assistant assistant) {
        if (assistant.equals(selectedAssistant)) {
            assistantMapImage.get(selectedAssistant).setEffect(null);
            selectedAssistant = null;
        }
        assistantMapImage.get(assistant).setEffect(new DropShadow(50, Color.GREEN));
        for (Assistant notSelected : Assistant.values()) {
            if (!notSelected.equals(assistant) && playableAssistant.contains(notSelected))
                assistantMapImage.get(notSelected).setEffect(null);
        }
        this.selectedAssistant = assistant;
    }

    /**
     * Complete the assistants choose phase
     */
    private void confirm() {
        if (selectedAssistant == null) {
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "You must select an assistant!");
            return;
        }
        disableAll();
        new Thread(() -> notifyObserver(obs -> obs.updateAssistant(selectedAssistant))).start();
        ((Stage) okButton.getScene().getWindow()).close();
    }

    public void init() {
        AnchorPane ap = (AnchorPane) okButton.getParent();
        Stage primaryStage = (Stage) okButton.getScene().getWindow();
        ap.setOnMousePressed(pressEvent -> ap.setOnMouseDragged(dragEvent -> {
            primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }
}