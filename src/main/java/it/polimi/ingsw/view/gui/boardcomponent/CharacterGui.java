package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.view.gui.GuiResources;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public record CharacterGui(ImageView characterImg, Label characterName, Label characterDescription, Label characterCost,
                           GridPane componentsOn, ImageView coinOn) {

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

    public void setPawnsOn(List<PawnColor> pawns) {
        int index = 0;
        for (int col = 0; col < 2; col++) {
            int row = 0;
            while (row < 3 && index < pawns.size()) {
                ImageView toAdd = new ImageView(GuiResources.getStudent(pawns.get(index)));
                toAdd.setPreserveRatio(true);
                toAdd.setFitHeight(30);
                index++;
                componentsOn.add(toAdd, col, row);
                row++;
            }
        }
    }

    public void setBansOn(int banTile) {
        int index = 0;
        for (int col = 0; col < 2; col++) {
            int row = 0;
            while (row < 3 && index < banTile) {
                ImageView toAdd = new ImageView(GuiResources.ban);
                toAdd.setPreserveRatio(true);
                toAdd.setFitHeight(30);
                index++;
                componentsOn.add(toAdd, col, row);
                row++;
            }
        }
    }

    public void setCoinOn(boolean hasCoinOn) {
        coinOn.setVisible(hasCoinOn);
    }

    public List<Node> contentsOn() {
        return List.of(characterImg, componentsOn, coinOn);
    }
}
