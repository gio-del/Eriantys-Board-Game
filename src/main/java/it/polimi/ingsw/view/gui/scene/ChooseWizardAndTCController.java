package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the controller of the wizard and tower color choosing scene
 */
public class ChooseWizardAndTCController extends ClientObservable implements BasicSceneController {
    private final Set<Wizard> wizardsAvailable;
    private final Set<TowerColor> colorsAvailable;
    private final Map<TowerColor, BorderPane> towerColorMapImage = new EnumMap<>(TowerColor.class);
    private final Map<Wizard, ImageView> wizardMapImage = new EnumMap<>(Wizard.class);
    private Wizard selectedWizard;
    private TowerColor selectedColor;
    @FXML
    private ImageView king;
    @FXML
    private ImageView sorcerer;
    @FXML
    private ImageView flameMagician;
    @FXML
    private ImageView witch;
    @FXML
    private BorderPane blackTower;
    @FXML
    private BorderPane whiteTower;
    @FXML
    private BorderPane greyTower;
    @FXML
    private Button okButton;
    @FXML
    private Group groupOfWizard;
    @FXML
    private ImageView loading;
    @FXML
    private ImageView background;


    public ChooseWizardAndTCController(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        this.wizardsAvailable = wizardsAvailable;
        this.colorsAvailable = colorsAvailable;
    }

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        towerColorMapImage.put(TowerColor.BLACK, blackTower);
        towerColorMapImage.put(TowerColor.WHITE, whiteTower);
        towerColorMapImage.put(TowerColor.GREY, greyTower);
        wizardMapImage.put(Wizard.KING, king);
        wizardMapImage.put(Wizard.SORCERER, sorcerer);
        wizardMapImage.put(Wizard.WITCH, witch);
        wizardMapImage.put(Wizard.SAGE, flameMagician);

        for (TowerColor color : TowerColor.values()) {
            if (!colorsAvailable.contains(color)) {
                disable(color);
            } else {
                towerColorMapImage.get(color).setCursor(Cursor.HAND);
                towerColorMapImage.get(color).setOnMouseClicked(evt -> selectTower(color));
            }
        }

        for (Wizard wizard : Wizard.values()) {
            if (!wizardsAvailable.contains(wizard)) {
                disable(wizard);
            } else {
                wizardMapImage.get(wizard).setCursor(Cursor.HAND);
                wizardMapImage.get(wizard).setOnMouseClicked(evt -> selectWizard(wizard));
            }
        }

        okButton.setCursor(Cursor.HAND);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> confirm());
    }

    private void disable(TowerColor color) {
        BorderPane pane = towerColorMapImage.get(color);
        ImageView imageView = (ImageView) pane.getCenter();
        Line line1 = new Line(pane.getLayoutX(), pane.getLayoutY(), pane.getLayoutX() + pane.getPrefWidth(), pane.getLayoutY() + pane.getPrefHeight());
        Line line2 = new Line(pane.getLayoutX() + pane.getPrefWidth(), pane.getLayoutY(), pane.getLayoutX(), pane.getLayoutY() + pane.getPrefHeight());
        Arrays.stream(new Line[]{line1, line2}).forEach(line -> {
            line.setStroke(Color.RED);
            line.setStrokeWidth(2);
        });
        ((AnchorPane) pane.getParent()).getChildren().addAll(line1, line2);
        disableImage(imageView);
    }

    private void disable(Wizard wizard) {
        disableImage(wizardMapImage.get(wizard));
    }

    private void disableImage(ImageView imageView) {
        ColorAdjust effect = new ColorAdjust();
        effect.setSaturation(-1d);
        imageView.setEffect(effect);
    }


    private void disableAll() {
        for (ImageView imageView : wizardMapImage.values()) {
            imageView.setDisable(true);
            imageView.setVisible(false);
        }
        for (BorderPane pane : towerColorMapImage.values()) {
            pane.setDisable(true);
            pane.setVisible(false);
        }
        okButton.setDisable(true);
        okButton.setVisible(false);
        AnchorPane pane = (AnchorPane) okButton.getParent();
        pane.getChildren().clear();
        pane.getChildren().addAll(loading, background, groupOfWizard);
        loading.setVisible(true);
        background.setVisible(true);
        groupOfWizard.setVisible(true);
    }

    /**
     * Complete the wizard and tower color choose phase
     */
    private void confirm() {
        if (selectedWizard == null || selectedColor == null) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "You must select Wizard and Tower Color!");
            return;
        }
        disableAll();
        new Thread(() -> notifyObserver(obs -> obs.updateWizardAndColor(selectedWizard, selectedColor))).start();
    }

    /**
     * Complete the tower color choose
     */
    private void selectTower(TowerColor color) {
        towerColorMapImage.get(color).setEffect(new DropShadow(50, Color.CORAL));
        for (TowerColor notSelected : TowerColor.values()) {
            if (!notSelected.equals(color) && colorsAvailable.contains(notSelected))
                towerColorMapImage.get(notSelected).setEffect(null);
        }
        this.selectedColor = color;
    }

    /**
     * Complete the wizard choose
     */
    private void selectWizard(Wizard wizard) {
        wizardMapImage.get(wizard).setEffect(new DropShadow(50, Color.GREEN));
        for (Wizard notSelected : Wizard.values()) {
            if (!notSelected.equals(wizard) && wizardsAvailable.contains(notSelected))
                wizardMapImage.get(notSelected).setEffect(null);
        }
        this.selectedWizard = wizard;
    }
}