package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class BoardSceneController extends ClientObservable {
    @FXML
    private ImageView useCharacter;
    @FXML
    private GridPane islandGrid;
    @FXML
    private ImageView school;
    @FXML
    private HBox cloudBox;
    @FXML
    private ImageView assistantDeck;

    @FXML
    private void initialize() {

        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                if(!(i==1 && (j==1 || j==2)) && !(i==2 && (j==1 || j==2))) {
                    ImageView image = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/islands/island_1.png"))));
                    image.setFitHeight(150);
                    image.setFitWidth(150);
                    islandGrid.add(image,i,j);
                    GridPane.setHalignment(image, HPos.CENTER);
                }
            }
        }
    }
}
