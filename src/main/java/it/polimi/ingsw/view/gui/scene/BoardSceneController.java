package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.boardcomponent.IslandGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

import static java.lang.System.exit;

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
                    IslandGui island = new IslandGui();
                    island.setAlignment(Pos.CENTER);
                    islandGrid.add(island,i,j);
                }
            }
        }
    }

    @FXML
    private void useCharacter(MouseEvent mouseEvent) {
        //TODO: check this
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/play_assistant.fxml"));
        PlayAssistantSceneController controller = new PlayAssistantSceneController();
        controller.setPlayableAssistant(Set.of(Assistant.values()));
        observers.forEach(controller::addObserver);
        loader.setController(controller);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            exit(1);
        }

        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
