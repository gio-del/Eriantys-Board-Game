package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

/**
 * This class implements the controller of the color choosing scene
 */
public class ColorCharacterSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private Button chooseBtn;
    @FXML
    private ImageView red;
    @FXML
    private ImageView yellow;
    @FXML
    private ImageView blue;
    @FXML
    private ImageView pink;
    @FXML
    private ImageView green;

    private PawnColor selected;

    private List<ImageView> colors;

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        colors = List.of(red, pink, yellow, blue, green);
    }

    /**
     * Select red color
     */
    public void redClick() {
        for (ImageView color : colors) {
            color.setEffect(null);
        }
        red.setEffect(new DropShadow(40, Color.YELLOW));
        this.selected = PawnColor.RED;
    }

    /**
     * Select yellow color
     */
    public void yellowClick() {
        for (ImageView color : colors) {
            color.setEffect(null);
        }
        yellow.setEffect(new DropShadow(40, Color.YELLOW));
        this.selected = PawnColor.YELLOW;
    }

    /**
     * Select blue color
     */
    public void blueClick() {
        for (ImageView color : colors) {
            color.setEffect(null);
        }
        blue.setEffect(new DropShadow(40, Color.YELLOW));
        this.selected = PawnColor.BLUE;
    }

    /**
     * Select pink color
     */
    public void pinkClick() {
        for (ImageView color : colors) {
            color.setEffect(null);
        }
        pink.setEffect(new DropShadow(40, Color.YELLOW));
        this.selected = PawnColor.PINK;
    }

    /**
     * Select green color
     */
    public void greenClick() {
        for (ImageView color : colors) {
            color.setEffect(null);
        }
        green.setEffect(new DropShadow(40, Color.YELLOW));
        this.selected = PawnColor.GREEN;
    }

    /**
     * Complete the color choose
     */
    public void choose() {
        if (selected == null) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "You must select a color");
            return;
        }
        new Thread(() -> notifyObserver(obs -> obs.updateColorAction(selected))).start();
        ((Stage) chooseBtn.getScene().getWindow()).close();
    }

    public void init() {
        AnchorPane ap = (AnchorPane) chooseBtn.getParent();
        Stage primaryStage = (Stage) chooseBtn.getScene().getWindow();
        ap.setOnMousePressed(pressEvent -> ap.setOnMouseDragged(dragEvent -> {
            primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }
}
