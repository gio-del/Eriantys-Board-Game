package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.ShortBoard;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.ShortPlayer;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.boardcomponent.CloudGui;
import it.polimi.ingsw.view.gui.boardcomponent.IslandGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static java.lang.System.exit;

public class BoardSceneController extends ClientObservable implements BasicSceneController {
    private final ShortModel resource;
    private final String nickname;
    @FXML
    private ImageView characterDeck;
    @FXML
    private Label owner;
    @FXML
    private GridPane islandGrid;
    @FXML
    private ImageView school;
    @FXML
    private HBox cloudBox;
    @FXML
    private HBox greenHall;
    @FXML
    private HBox yellowHall;
    @FXML
    private HBox pinkHall;
    @FXML
    private HBox blueHall;
    @FXML
    private HBox redHall;
    @FXML
    private GridPane profTable;
    @FXML
    private GridPane towerGrid;

    @FXML
    private ImageView assistantDeck;
    private Set<Assistant> playableAssistant;

    public BoardSceneController(ShortModel resource, String nickname) {
        this.resource = resource;
        this.nickname = nickname;

    }

    @FXML
    private void initialize() {
        ShortPlayer myself = resource.getSchoolMap().keySet().stream().filter(player -> player.name().equals(nickname)).findFirst().orElse(null);
        assert myself != null;
        owner.setText(myself.name());
        assistantDeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/wizards/" + myself.wizard().name().toLowerCase() + "_deck.png"))));
        if (resource.getCharacters() != null)
            characterDeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/characters/CharacterBack.png"))));
        printIslands();
        printClouds();
    }

    @FXML
    private void useAssistant(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/play_assistant.fxml"));
        PlayAssistantSceneController controller = new PlayAssistantSceneController();
        controller.setPlayableAssistant(playableAssistant);
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
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/various/king_nobg.png"))));
        stage.setTitle("Choose an assistant from the available ones");
        stage.showAndWait();

        assistantDeck.setOnMouseClicked(null);
        assistantDeck.setCursor(Cursor.DEFAULT);
    }

    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
        assistantDeck.setOnMouseClicked(this::useAssistant);
        assistantDeck.setCursor(Cursor.HAND);
    }

    private void printSchools() {
        //TODO
    }

    private void printClouds() {
        for (ShortCloud shortCloud : resource.getClouds()) {
            cloudBox.getChildren().add(new CloudGui(shortCloud));
        }

    }

    private void printIslands() {
        int k = 0; //todo: check if islands are less than 12
        ShortBoard board = resource.getBoard();
        for (int i = 0; i < 4; i++) {
            IslandGui islandGui = new IslandGui(board.getIslands().get(k), board.getMotherNaturePos() == k++);
            HBox hBox = new HBox(islandGui);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(hBox);
            vBox.setAlignment(Pos.TOP_CENTER);
            int finalCol = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCol && GridPane.getRowIndex(node) == 0);
            islandGrid.add(vBox, i, 0);
        }
        for (int i = 1; i < 4; i++) {
            IslandGui islandGui = new IslandGui(board.getIslands().get(k), board.getMotherNaturePos() == k++);
            HBox hBox = new HBox(islandGui);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(hBox);
            vBox.setAlignment(Pos.TOP_CENTER);
            int finalRow = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 3 && GridPane.getRowIndex(node) == finalRow);
            islandGrid.add(vBox, 3, i);
        }
        for (int i = 2; i >= 0; i--) {
            IslandGui islandGui = new IslandGui(board.getIslands().get(k), board.getMotherNaturePos() == k++);
            HBox hBox = new HBox(islandGui);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(hBox);
            vBox.setAlignment(Pos.TOP_CENTER);
            int finalCol = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCol && GridPane.getRowIndex(node) == 3);
            islandGrid.add(vBox, i, 3);
        }
        for (int i = 2; i >= 1; i--) {
            IslandGui islandGui = new IslandGui(board.getIslands().get(k), board.getMotherNaturePos() == k++);
            HBox hBox = new HBox(islandGui);
            hBox.setAlignment(Pos.CENTER);
            VBox vBox = new VBox(hBox);
            vBox.setAlignment(Pos.TOP_CENTER);
            int finalRow = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == finalRow);
            islandGrid.add(vBox, 0, i);
        }
    }

    public void refresh() {
        printIslands();
    }
}
