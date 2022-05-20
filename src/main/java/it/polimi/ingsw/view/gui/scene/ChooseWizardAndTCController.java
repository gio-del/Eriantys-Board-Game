package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
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
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class ChooseWizardAndTCController extends ClientObservable implements BasicSceneController {
    private final Set<Wizard> wizardsAvailable;
    private final Set<TowerColor> colorsAvailable;
    private final Map<TowerColor, ImageView> towerColorMapImage = new EnumMap<>(TowerColor.class);
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
    private ImageView blackTower;
    @FXML
    private ImageView whiteTower;
    @FXML
    private ImageView greyTower;
    @FXML
    private Button okButton;


    public ChooseWizardAndTCController(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        this.wizardsAvailable = wizardsAvailable;
        this.colorsAvailable = colorsAvailable;
    }

    @FXML
    private void initialize() {
        towerColorMapImage.put(TowerColor.BLACK, blackTower);
        towerColorMapImage.put(TowerColor.WHITE, whiteTower);
        towerColorMapImage.put(TowerColor.GRAY, greyTower);
        wizardMapImage.put(Wizard.KING, king);
        wizardMapImage.put(Wizard.SORCERER, sorcerer);
        wizardMapImage.put(Wizard.WITCH, witch);
        wizardMapImage.put(Wizard.SAGE, flameMagician);

        for (TowerColor color : TowerColor.values()) {
            if (!colorsAvailable.contains(color)) {
                disable(color);
            } else {
                towerColorMapImage.get(color).setCursor(Cursor.HAND);
                towerColorMapImage.get(color).addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> selectTower(color));
            }
        }

        for (Wizard wizard : Wizard.values()) {
            if (!wizardsAvailable.contains(wizard)) {
                disable(wizard);
            } else {
                wizardMapImage.get(wizard).setCursor(Cursor.HAND);
                wizardMapImage.get(wizard).addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> selectWizard(wizard));
            }
        }

        okButton.setCursor(Cursor.HAND);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> confirm());
    }

    private void disable(TowerColor color) {
        ImageView imageView = towerColorMapImage.get(color);
        Line line1 = new Line(imageView.getLayoutX(), imageView.getLayoutY(), imageView.getLayoutX() + imageView.getFitWidth(), imageView.getLayoutY() + imageView.getFitHeight());
        Line line2 = new Line(imageView.getLayoutX() + imageView.getFitWidth(), imageView.getLayoutY(), imageView.getLayoutX(), imageView.getLayoutY() + imageView.getFitHeight());
        Arrays.stream(new Line[]{line1,line2}).forEach(line -> {
            line.setStroke(Color.RED);
            line.setStrokeWidth(2);
        });
        ((AnchorPane) imageView.getParent()).getChildren().addAll(line1, line2);
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
        }
        for (ImageView imageView : towerColorMapImage.values()) {
            imageView.setDisable(true);
        }
        okButton.setDisable(true);
    }

    private void confirm() {
        if (selectedWizard == null || selectedColor == null) {
            SceneController.showAlert(Alert.AlertType.WARNING, "You must select Wizard and Tower Color!");
            return;
        }
        disableAll();
        new Thread(() -> notifyObserver(obs -> obs.updateWizardAndColor(selectedWizard, selectedColor))).start();
    }

    private void selectTower(TowerColor color) {
        towerColorMapImage.get(color).setEffect(new DropShadow(50, Color.CORAL));
        for (TowerColor notSelected : TowerColor.values()) {
            if (!notSelected.equals(color) && colorsAvailable.contains(notSelected))
                towerColorMapImage.get(notSelected).setEffect(null);
        }
        this.selectedColor = color;
    }

    private void selectWizard(Wizard wizard) {
        wizardMapImage.get(wizard).setEffect(new DropShadow(50, Color.GREEN));
        for (Wizard notSelected : Wizard.values()) {
            if (!notSelected.equals(wizard) && wizardsAvailable.contains(notSelected))
                wizardMapImage.get(notSelected).setEffect(null);
        }
        this.selectedWizard = wizard;
    }
}