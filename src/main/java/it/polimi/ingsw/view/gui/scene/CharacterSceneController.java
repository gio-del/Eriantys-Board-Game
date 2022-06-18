package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.character.ShortCharacter;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.boardcomponent.CharacterGui;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the controller of the characters scene
 */
public class CharacterSceneController extends ClientObservable implements BasicSceneController {
    private final List<CharacterGui> characterComponents;
    @FXML
    private Button okButton;
    @FXML
    private ImageView firstCharacterImg;
    @FXML
    private ImageView secondCharacterImg;
    @FXML
    private ImageView thirdCharacterImg;
    @FXML
    private Label firstCharacterName;
    @FXML
    private Label secondCharacterName;
    @FXML
    private Label thirdCharacterName;
    @FXML
    private Label firstCharacterCost;
    @FXML
    private Label secondCharacterCost;
    @FXML
    private Label thirdCharacterCost;
    @FXML
    private Label firstCharacterDescription;
    @FXML
    private Label secondCharacterDescription;
    @FXML
    private Label thirdCharacterDescription;
    @FXML
    private GridPane firstComponentsOn;
    @FXML
    private GridPane secondComponentsOn;
    @FXML
    private GridPane thirdComponentsOn;
    @FXML
    private ImageView firstCoinOn;
    @FXML
    private ImageView secondCoinOn;
    @FXML
    private ImageView thirdCoinOn;
    private int selectedCharacter = -1;
    private List<ShortCharacter> characters;

    public CharacterSceneController() {
        characterComponents = new ArrayList<>();
    }

    public void setCharactersInUse(List<ShortCharacter> characters) {
        this.characters = characters;
    }

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        characterComponents.add(new CharacterGui(firstCharacterImg, firstCharacterName, firstCharacterDescription, firstCharacterCost, firstComponentsOn, firstCoinOn));
        characterComponents.add(new CharacterGui(secondCharacterImg, secondCharacterName, secondCharacterDescription, secondCharacterCost, secondComponentsOn, secondCoinOn));
        characterComponents.add(new CharacterGui(thirdCharacterImg, thirdCharacterName, thirdCharacterDescription, thirdCharacterCost, thirdComponentsOn, thirdCoinOn));

        for (int i = 0; i < characters.size(); i++) {
            ShortCharacter character = characters.get(i);
            CharacterGui component = characterComponents.get(i);
            component.contentsOn().forEach(content -> content.setCursor(Cursor.HAND));
            int finalI = i;
            component.contentsOn().forEach(content -> content.setOnMouseClicked(evt -> selectCharacter(finalI)));
            component.setCharacterImg(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/characters/" + character.getName().replace(" ", "_").toLowerCase() + ".jpg"))));
            component.setCharacterCost(character.getCost());
            component.setCharacterDescription(character.getDescription());
            component.setCharacterName(character.getName());
            component.setPawnsOn(character.getStudentsOn().toList());
            component.setBansOn(character.getBanTiles());
            component.setCoinOn(character.hasCoinOn());
        }

        okButton.setOnMouseClicked(evt -> chooseCharacter());
    }

    private void selectCharacter(int characterID) {
        if (characterID == selectedCharacter) {
            selectedCharacter = -1;
            characterComponents.get(characterID).characterImg().setEffect(null);
            return;
        }
        for (CharacterGui characterGui : characterComponents) {
            characterGui.characterImg().setEffect(null);
        }
        characterComponents.get(characterID).characterImg().setEffect(new DropShadow(50, Color.GREEN));
        selectedCharacter = characterID;
    }

    /**
     * Complete the character choose
     */
    private void chooseCharacter() {
        new Thread(() -> notifyObserver(obs -> obs.updateUseCharacter(selectedCharacter))).start();
        ((Stage) okButton.getScene().getWindow()).close();
    }
}
