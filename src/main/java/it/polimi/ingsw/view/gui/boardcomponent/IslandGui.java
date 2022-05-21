package it.polimi.ingsw.view.gui.boardcomponent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class IslandGui extends StackPane {
    ImageView imageView;

    public IslandGui() {
        imageView = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/islands/island_1.png"))));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        getChildren().addAll(imageView);
    }
}
