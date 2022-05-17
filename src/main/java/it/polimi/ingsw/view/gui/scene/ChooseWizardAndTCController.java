package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Set;

public class ChooseWizardAndTCController extends ClientObservable implements BasicSceneController {
    private final Set<Wizard> wizardsAvailable;
    private final Set<TowerColor> colorsAvailable;

    private Wizard selectedWizard;
    private TowerColor selectedColor;

    @FXML private ImageView king;
    @FXML private ImageView sorcerer;
    @FXML private ImageView flameMagician;
    @FXML private ImageView witch;

    @FXML private ImageView blackTower;
    @FXML private ImageView whiteTower;
    @FXML private ImageView greyTower;

    @FXML private Button okButton;
    public ChooseWizardAndTCController(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        this.wizardsAvailable = wizardsAvailable;
        this.colorsAvailable = colorsAvailable;
    }

    @FXML
    private void initialize() {
        king.setDisable(!wizardsAvailable.contains(Wizard.KING));
        sorcerer.setDisable(!wizardsAvailable.contains(Wizard.SORCERER));
        flameMagician.setDisable(!wizardsAvailable.contains(Wizard.FLAME_MAGICIAN));
        witch.setDisable(!wizardsAvailable.contains(Wizard.WITCH));

        blackTower.setDisable(!colorsAvailable.contains(TowerColor.BLACK));
        greyTower.setDisable(!colorsAvailable.contains(TowerColor.GRAY));
        whiteTower.setDisable(!colorsAvailable.contains(TowerColor.WHITE));

        king.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectWizard(Wizard.KING));
        flameMagician.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectWizard(Wizard.FLAME_MAGICIAN));
        sorcerer.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectWizard(Wizard.SORCERER));
        witch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectWizard(Wizard.WITCH));

        blackTower.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectTower(TowerColor.BLACK));
        whiteTower.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectTower(TowerColor.WHITE));
        greyTower.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selectTower(TowerColor.GRAY));

        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> confirm());
    }

    private void confirm() {
        new Thread(() -> notifyObserver(obs -> obs.updateWizardAndColor(selectedWizard,selectedColor))).start();
    }

    private void selectTower(TowerColor color) {
        this.selectedColor = color;
    }

    private void selectWizard(Wizard wizard) {
        this.selectedWizard = wizard;
    }
}