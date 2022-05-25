package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class SwapCharacterSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView green2;
    @FXML
    private ImageView red2;
    @FXML
    private ImageView yellow2;
    @FXML
    private ImageView blue2;
    @FXML
    private ImageView pink2;
    @FXML
    private Button stopBtn;
    @FXML
    private ImageView green1;
    @FXML
    private ImageView red1;
    @FXML
    private ImageView yellow1;
    @FXML
    private ImageView pink1;
    @FXML
    private ImageView blue1;
    private int maxSwaps;
    private final List<PawnColor> swapList;
    private final Map<ImageView,PawnColor> firstColors;
    private final Map<ImageView,PawnColor> secondColors;
    private PawnColor selectedFirstColor;
    private PawnColor selectedSecondColor;
    private static final String MESSAGE = "Choose a couple of color to swap";

    public SwapCharacterSceneController() {
        this.swapList = new ArrayList<>();
        this.firstColors = new HashMap<>();
        this.secondColors = new HashMap<>();
    }

    public void setMaxSwaps(int maxSwaps) {
        infoLabel.setText(MESSAGE + " [Remaining: "+(maxSwaps-swapList.size()/2)+"]");
        this.maxSwaps = maxSwaps;
    }

    @FXML
    private void initialize() {
        firstColors.put(blue1,PawnColor.BLUE);
        firstColors.put(red1, PawnColor.RED);
        firstColors.put(yellow1, PawnColor.YELLOW);
        firstColors.put(pink1, PawnColor.PINK);
        firstColors.put(green1, PawnColor.GREEN);

        secondColors.put(blue2, PawnColor.BLUE);
        secondColors.put(red2, PawnColor.RED);
        secondColors.put(yellow2, PawnColor.YELLOW);
        secondColors.put(pink2, PawnColor.PINK);
        secondColors.put(green2, PawnColor.GREEN);
    }

    public void firstColorChoose(MouseEvent mouseEvent) {
        ImageView selectedImage = (ImageView)mouseEvent.getSource();

        firstColors.keySet().forEach(img -> img.setEffect(null));
        selectedImage.setEffect(new DropShadow(50, Color.YELLOW));

        selectedFirstColor = firstColors.get(selectedImage);
    }

    public void secondColorChoose(MouseEvent mouseEvent) {
        ImageView selectedImage = (ImageView)mouseEvent.getSource();

        secondColors.keySet().forEach(img -> img.setEffect(null));
        selectedImage.setEffect(new DropShadow(50, Color.YELLOW));

        selectedSecondColor = secondColors.get(selectedImage);
    }

    public void continueSwapping() {
        infoLabel.setText(MESSAGE + " [Remaining: "+(maxSwaps-swapList.size()/2)+"]");
        if(maxSwaps - swapList.size()/2 == 0) {
            stopSwapping();
            return;
        }
        swapList.addAll(List.of(selectedFirstColor,selectedSecondColor));
    }

    public void stopSwapping() {
        if(swapList.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.WARNING,"Perform almost one swap!");
            return;
        }
        new Thread(() -> notifyObserver(obs -> obs.updateSwapAction(swapList))).start();
        ((Stage) stopBtn.getScene().getWindow()).close();
    }
}
