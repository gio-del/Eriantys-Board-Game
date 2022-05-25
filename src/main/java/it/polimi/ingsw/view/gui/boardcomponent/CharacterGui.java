package it.polimi.ingsw.view.gui.boardcomponent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public record CharacterGui(ImageView characterImg, Label characterName, Label characterDescription, Label characterCost) {

    public void setCharacterImg(Image characterImg) {
        this.characterImg.setImage(characterImg);
    }

    public void setCharacterName(String name) {
        this.characterName.setText(name);
    }

    public void setCharacterDescription(String description) {
        this.characterDescription.setText(description);
    }

    public void setCharacterCost(int cost) {
        this.characterCost.setText(this.characterCost.getText() + " " + cost);
    }
}
