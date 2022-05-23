package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.ShortBoard;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.ShortPlayer;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.boardcomponent.CloudGui;
import it.polimi.ingsw.view.gui.boardcomponent.IslandGui;
import it.polimi.ingsw.view.gui.boardcomponent.SchoolGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;

public class BoardSceneController extends ClientObservable implements BasicSceneController {
    private final ShortModel resource;
    private final String nickname;
    @FXML
    private ImageView characterDeck;
    @FXML
    private GridPane islandGrid;
    @FXML
    private HBox cloudBox;
    @FXML
    private Label schoolOwner;
    @FXML
    private ImageView school;
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
    private GridPane entranceGrid;
    @FXML
    private ImageView assistantDeck;
    @FXML
    private Button nextSchoolBtn;
    @FXML
    private Button prevSchoolBtn;
    private Set<Assistant> playableAssistant;
    private final Map<String,SchoolGui> schoolGuiMap;
    private SchoolGui actualSchool;
    private final List<String> names;
    private final Map<PawnColor,HBox> hallMap;

    public BoardSceneController(ShortModel resource, String nickname) {
        this.resource = resource;
        this.nickname = nickname;
        this.schoolGuiMap = new HashMap<>();
        this.hallMap = new EnumMap<>(PawnColor.class);
        this.names = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        ShortPlayer myself = resource.getSchoolMap().keySet().stream().filter(player -> player.name().equals(nickname)).findFirst().orElse(null);
        assert myself != null;

        //ASSISTANTS
        assistantDeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/wizards/" + myself.wizard().name().toLowerCase() + "_deck.png"))));

        //CHARACTERS
        if (resource.getCharacters() != null)
            characterDeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/characters/CharacterBack.png"))));

        //CLOUDS
        for (ShortCloud shortCloud : resource.getClouds()) {
            cloudBox.getChildren().add(new CloudGui(shortCloud));
        }

        //SCHOOLS
        hallMap.putAll(Map.of(PawnColor.GREEN,greenHall,PawnColor.BLUE,blueHall,PawnColor.YELLOW,yellowHall,PawnColor.PINK,pinkHall,PawnColor.RED,redHall));
        resource.getSchoolMap().forEach((key, value) -> schoolGuiMap.put(key.name(), new SchoolGui(key, value)));
        actualSchool = schoolGuiMap.get(nickname);
        names.add(nickname);
        schoolGuiMap.keySet().stream().filter(name -> !name.equals(nickname)).forEach(names::add);

        //nextSchoolBtn and prevSchoolBtn
        nextSchoolBtn.setOnAction(event -> this.nextSchool());
        prevSchoolBtn.setDisable(true);

        refresh();
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
        assistantDeck.setEffect(null);
        assistantDeck.setCursor(Cursor.DEFAULT);
    }

    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
        assistantDeck.setOnMouseClicked(this::useAssistant);
        assistantDeck.setEffect(new DropShadow(40, Color.YELLOW));
        assistantDeck.setCursor(Cursor.HAND);
    }

    private void printSchool() {

        //OWNER
        schoolOwner.setText(actualSchool.getOwner());

        //HALL
        for(PawnColor pawnColor: PawnColor.values()) {
            this.hallMap.get(pawnColor).getChildren().clear();
            this.hallMap.get(pawnColor).getChildren().addAll(actualSchool.getHallViewsMap().get(pawnColor));
        }

        //ENTRANCE
        List<ImageView> entranceViews = actualSchool.getEntranceViews();
        entranceGrid.getChildren().clear();
        int index = 0;
        for(int row=0;row<5 && index<entranceViews.size();row++){
            for(int col=0;col<2;col++){
                if(!(row==0 && col==0)) {
                    entranceGrid.add(entranceViews.get(index),col,row);
                    index++;
                }
            }
        }

        //TOWER
        List<ImageView> towerViews = actualSchool.getTowerViews();
        towerGrid.getChildren().clear();
        index = 0;
        for(int row=0;row<4 && index<towerViews.size();row++){
            for(int col=0;col<2;col++){
                towerGrid.add(towerViews.get(index),col,row);
                index++;
            }
        }

        //PROF TABLE
        profTable.getChildren().clear();
        Map<PawnColor,ImageView> profImageMap = actualSchool.getProfessorsViews();
        for(PawnColor pawnColor: PawnColor.values()) {
            profTable.add(profImageMap.get(pawnColor),0,pawnColor.ordinal());
        }

    }

    private void printClouds() {
        for(int i =0 ;i<resource.getClouds().size();i++) {
            ((CloudGui)cloudBox.getChildren().get(i)).setAs(resource.getClouds().get(i));
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

    private void nextSchool() {
        int index = names.indexOf(actualSchool.getOwner());
        if(index == names.size()-2) {
            nextSchoolBtn.setDisable(true);
            nextSchoolBtn.setOnAction(null);
        }
        if(index == 0) {
            prevSchoolBtn.setDisable(false);
            prevSchoolBtn.setOnAction(event -> this.prevSchool());
        }
        actualSchool = schoolGuiMap.get(names.get(index+1));
        printSchool();
    }

    private void prevSchool() {
        int index = names.indexOf(actualSchool.getOwner());
        if(index == 1) {
            prevSchoolBtn.setDisable(true);
            prevSchoolBtn.setOnAction(null);
        }
        if(index == names.size() - 1 ) {
            nextSchoolBtn.setDisable(false);
            nextSchoolBtn.setOnAction(event -> this.nextSchool());
        }
        actualSchool = schoolGuiMap.get(names.get(index-1));
        printSchool();
    }

    public void refresh() {
        for(Map.Entry<ShortPlayer, ShortSchool> entry: resource.getSchoolMap().entrySet()) {
            schoolGuiMap.get(entry.getKey().name()).refresh(entry.getKey(),entry.getValue());
        }
        printIslands();
        printClouds();
        printSchool();
    }
}
