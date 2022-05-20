package it.polimi.ingsw.view.gui.boardcomponent;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IslandGui extends Node {
    ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/images/islands/island_1.png")));
}
