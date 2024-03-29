package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.ShortBoard;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.ShortPlayer;
import it.polimi.ingsw.network.communication.Target;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import it.polimi.ingsw.view.gui.boardcomponent.CloudGui;
import it.polimi.ingsw.view.gui.boardcomponent.GuiResources;
import it.polimi.ingsw.view.gui.boardcomponent.IslandGui;
import it.polimi.ingsw.view.gui.boardcomponent.SchoolGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.exit;

/**
 * This class implements the controller of the board scene, the main of the game
 */
public class BoardSceneController extends ClientObservable implements BasicSceneController {
    private static final DropShadow SHADOW = new DropShadow(8, Color.BLACK);
    private final ShortModel resource;
    private final String nickname;
    private final Map<String, SchoolGui> schoolGuiMap;
    private final List<IslandGui> islandGuiList;
    private final List<String> names;
    private final Map<PawnColor, HBox> hallMap;
    @FXML
    private Label studentsInSack;
    @FXML
    private ImageView characterDeck;
    @FXML
    private ImageView lastPlayedAssistant;
    @FXML
    private GridPane islandGrid;
    @FXML
    private HBox cloudBox;
    @FXML
    private Label schoolOwner;
    @FXML
    private Label coin;
    @FXML
    private ImageView coinImg;
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
    private BorderPane assistantDeck;
    @FXML
    private Button nextSchoolBtn;
    @FXML
    private Button prevSchoolBtn;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView muteBtn;
    private Set<Assistant> playableAssistant;
    private SchoolGui actualSchool;
    private PawnColor selectedColor;
    private boolean canPlayCharacter;
    private MediaPlayer player;


    public BoardSceneController(ShortModel resource, String nickname) {
        this.resource = resource;
        this.nickname = nickname;
        this.schoolGuiMap = new HashMap<>();
        this.hallMap = new EnumMap<>(PawnColor.class);
        this.names = new ArrayList<>();
        this.islandGuiList = new ArrayList<>();
    }

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        ShortPlayer myself = resource.getSchoolMap().keySet().stream().filter(p -> p.name().equals(nickname)).findFirst().orElse(null);
        assert myself != null;

        //MUTE AND BACK TO MENU
        muteBtn.setOnMouseClicked(evt -> switchMute());

        //CHARACTERS
        if (resource.getCharacters() != null) {
            canPlayCharacter = false;
            characterDeck.setImage(GuiResources.characterBack);
            characterDeck.setOnMouseClicked(evt -> useCharacter());
            characterDeck.setCursor(Cursor.HAND);
            characterDeck.setEffect(SHADOW);
        }

        //COIN
        if (resource.getMoneyMap() != null) {
            coinImg.setVisible(true);
            coinImg.setEffect(SHADOW);
        }

        //CLOUDS
        for (ShortCloud shortCloud : resource.getClouds()) {
            cloudBox.getChildren().add(new CloudGui(shortCloud));
        }

        //SCHOOLS
        hallMap.putAll(Map.of(PawnColor.GREEN, greenHall, PawnColor.BLUE, blueHall, PawnColor.YELLOW, yellowHall, PawnColor.PINK, pinkHall, PawnColor.RED, redHall));
        resource.getSchoolMap().forEach((key, value) -> schoolGuiMap.put(key.name(), new SchoolGui(key, value)));
        actualSchool = schoolGuiMap.get(nickname);
        names.add(nickname);
        schoolGuiMap.keySet().stream().filter(name -> !name.equals(nickname)).forEach(names::add);

        //ISLANDS
        for (int i = 0; i < resource.getBoard().getIslands().size(); i++) {
            islandGuiList.add(new IslandGui(resource.getBoard().getIslands().get(i), resource.getBoard().getMotherNaturePos() == i));
        }

        //LAST PLAYED ASSISTANT
        lastPlayedAssistant.setEffect(SHADOW);

        //nextSchoolBtn and prevSchoolBtn
        nextSchoolBtn.setOnAction(event -> this.nextSchool());
        nextSchoolBtn.setCursor(Cursor.HAND);
        prevSchoolBtn.setDisable(true);

        refresh();
    }

    private void switchMute() {
        if (player.isMute()) {
            muteBtn.setImage(GuiResources.soundOn);
            player.setMute(false);
        } else {
            muteBtn.setImage(GuiResources.soundOff);
            player.setMute(true);
        }
    }

    /**
     * Set the list of playable assistants
     */
    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        consumeEventChooseCloud();
        consumeEventMoveMotherNature();
        consumeEventMoveStudent(true);
        this.canPlayCharacter = false;
        infoLabel.setText("Select your assistant deck to choose one!");
        this.playableAssistant = playableAssistant;
        ImageView myWizard = schoolGuiMap.get(nickname).getWizard();
        myWizard.setOnMouseClicked(evt -> useAssistant());
        myWizard.setEffect(new DropShadow(25, Color.YELLOW));
        myWizard.setCursor(Cursor.HAND);
    }

    /**
     * To choose an assistant
     */
    private void useAssistant() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/play_assistant.fxml"));
        PlayAssistantSceneController controller = new PlayAssistantSceneController();
        controller.setPlayableAssistant(playableAssistant);
        observers.forEach(controller::addObserver);
        loader.setController(controller);

        Parent root;

        try {
            root = loader.load();
            stage.setScene(new Scene(root));
            controller.init();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            exit(1);
        }

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/various/king_nobg.png"))));
        stage.setTitle("Choose an assistant from the available ones");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        ImageView myWizard = schoolGuiMap.get(nickname).getWizard();
        myWizard.setOnMouseClicked(null);
        myWizard.setEffect(SHADOW);
        myWizard.setCursor(Cursor.DEFAULT);
    }

    /**
     * To use a character
     */
    private void useCharacter() {

        if (!canPlayCharacter) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Wait your action turn to play a character card!");
            return;
        }

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/characters.fxml"));
        CharacterSceneController controller = new CharacterSceneController();
        controller.setCharactersInUse(resource.getCharacters());
        observers.forEach(controller::addObserver);
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            exit(1);
        }
        stage.setScene(new Scene(root));
        stage.getIcons().add(GuiResources.characterBack);
        stage.setTitle("Choose a character to play!");
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    /**
     * To update the school
     */
    private void printSchool() {

        //OWNER
        String owner = actualSchool.getOwner();
        if (owner.equals(nickname)) owner = owner + " [you]";
        schoolOwner.setText(owner);

        //LAST PLAYED ASSISTANT
        Assistant assistant = resource.getLastPlayedAssistant(actualSchool.getOwner());
        if (assistant == null) {
            lastPlayedAssistant.setImage(null);
        } else lastPlayedAssistant.setImage(GuiResources.getAssistant(assistant));

        //COIN
        if (coinImg.isVisible())
            coin.setText("x" + resource.getMoneyMap().get(actualSchool.getOwner()));

        //ASSISTANT DECK
        assistantDeck.setCenter(actualSchool.getWizard());
        actualSchool.getWizard().setEffect(SHADOW);

        //HALL
        for (PawnColor pawnColor : PawnColor.values()) {
            this.hallMap.get(pawnColor).getChildren().clear();
            this.hallMap.get(pawnColor).getChildren().addAll(actualSchool.getHallViewsMap().get(pawnColor));
        }

        //ENTRANCE
        List<ImageView> entranceViews = new ArrayList<>();
        for (PawnColor pawnColor : PawnColor.values()) {
            entranceViews.addAll(actualSchool.getEntranceViews().get(pawnColor));
        }
        entranceGrid.getChildren().clear();
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 2 && index < entranceViews.size(); col++) {
                if (!(row == 0 && col == 0)) {
                    entranceGrid.add(entranceViews.get(index), col, row);
                    index++;
                }
            }
        }

        //TOWER
        List<ImageView> towerViews = actualSchool.getTowerViews();
        towerGrid.getChildren().clear();
        index = 0;
        for (int row = 0; row < 4; row++) {
            int col = 0;
            while (col < 2 && index < towerViews.size()) {
                towerGrid.add(towerViews.get(index), col, row);
                index++;
                col++;
            }
        }

        //PROF TABLE
        profTable.getChildren().clear();
        Map<PawnColor, ImageView> profImageMap = actualSchool.getProfessorsViews();
        for (PawnColor pawnColor : PawnColor.values()) {
            profTable.add(profImageMap.get(pawnColor), 0, pawnColor.ordinal());
        }

        actualSchool.getContent().forEach(content -> content.setEffect(new DropShadow(4, Color.BLACK)));

    }

    /**
     * To update the clouds
     */
    private void printClouds() {
        IntStream.range(0, resource.getClouds().size()).forEach(i -> {
                    CloudGui cloudGui = (CloudGui) cloudBox.getChildren().get(i);
                    cloudGui.setAs(resource.getClouds().get(i));
                    cloudGui.getContentView().forEach(cloud -> cloud.setEffect(new DropShadow(2, Color.BLACK)));
                }
        );
    }

    private VBox buildIsland(ShortBoard board, int index) {
        IslandGui islandGui = islandGuiList.get(index);
        islandGui.refresh(board.getIslands().get(index), board.getMotherNaturePos() == index);
        HBox hBox = new HBox(islandGui);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * To update the islands
     */
    private void refreshIslands() {
        int index = 0;
        ShortBoard board = resource.getBoard();
        int boardSize = board.getIslands().size();
        islandGuiList.forEach(islandGui -> islandGui.getContentOnIsland().forEach(content -> content.setEffect(SHADOW)));
        int i;

        i = 0;
        while (i < 4 && index < boardSize) {
            VBox vBox = buildIsland(board, index);
            index++;
            int finalCol = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCol && GridPane.getRowIndex(node) == 0);
            islandGrid.add(vBox, i, 0);
            i++;
        }
        i = 1;
        while (i < 4 && index < boardSize) {
            VBox vBox = buildIsland(board, index);
            index++;
            int finalRow = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 3 && GridPane.getRowIndex(node) == finalRow);
            islandGrid.add(vBox, 3, i);
            i++;
        }
        i = 2;
        while (i >= 0 && index < boardSize) {
            VBox vBox = buildIsland(board, index);
            index++;
            int finalCol = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == finalCol && GridPane.getRowIndex(node) == 3);
            islandGrid.add(vBox, i, 3);
            i--;
        }
        i = 2;
        while (i >= 1 && index < boardSize) {
            VBox vBox = buildIsland(board, index);
            index++;
            int finalRow = i;
            islandGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == finalRow);
            islandGrid.add(vBox, 0, i);
            i--;
        }
        for (; index < islandGuiList.size(); index++) {
            islandGuiList.get(index).delete();
            islandGuiList.remove(index);
        }
    }

    /**
     * Shows the next player's school
     */
    private void nextSchool() {
        int index = names.indexOf(actualSchool.getOwner());
        if (index == names.size() - 2) {
            nextSchoolBtn.setDisable(true);
            nextSchoolBtn.setOnAction(null);
        }
        if (index == 0) {
            prevSchoolBtn.setDisable(false);
            prevSchoolBtn.setCursor(Cursor.HAND);
            prevSchoolBtn.setOnAction(event -> this.prevSchool());
        }
        actualSchool = schoolGuiMap.get(names.get(index + 1));
        printSchool();
    }

    /**
     * Shows the previous player's school
     */
    private void prevSchool() {
        int index = names.indexOf(actualSchool.getOwner());
        if (index == 1) {
            prevSchoolBtn.setDisable(true);
            prevSchoolBtn.setOnAction(null);
        }
        if (index == names.size() - 1) {
            nextSchoolBtn.setDisable(false);
            nextSchoolBtn.setCursor(Cursor.HAND);
            nextSchoolBtn.setOnAction(event -> this.nextSchool());
        }
        actualSchool = schoolGuiMap.get(names.get(index - 1));
        printSchool();
    }

    /**
     * To update the board
     */
    public void refresh() {
        for (Map.Entry<ShortPlayer, ShortSchool> entry : resource.getSchoolMap().entrySet()) {
            schoolGuiMap.get(entry.getKey().name()).refresh(entry.getKey(), entry.getValue());
        }

        //SACK
        this.studentsInSack.setText(String.valueOf(resource.getStudentsInSack()));

        refreshIslands();
        printClouds();
        printSchool();
    }

    /**
     * Set the movable students
     */
    public void setMovableStudents() {
        consumeEventChooseCloud();
        consumeEventMoveMotherNature();
        consumeEventMoveStudent(true);

        this.canPlayCharacter = true;
        infoLabel.setText("Drag a student to move from your entrance to your hall or island!");

        Map<PawnColor, List<ImageView>> colorMapImageViews = schoolGuiMap.get(nickname).getEntranceViews();
        colorMapImageViews.forEach((pawnColor, imageViews) -> imageViews.forEach(iv -> {
            iv.setOnDragDetected(evt -> {
                Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setTransform(Transform.scale(3, 3));
                snapshotParameters.setFill(Color.TRANSPARENT);
                WritableImage image = iv.snapshot(snapshotParameters, null);
                content.putImage(image);
                db.setContent(content);
                chooseMoveColor(pawnColor);
                iv.setVisible(false);
                evt.consume();
            });
            iv.setOnDragDone(evt -> {
                consumeEventMoveStudent(false);
                iv.setVisible(true);
                evt.consume();
            });
            iv.setCursor(Cursor.HAND);
        }));


        for (int i = 0; i < islandGuiList.size(); i++) {
            int finalI = i;
            islandGuiList.get(i).getContentOnIsland().forEach(
                    iv -> iv.setOnDragDropped(evt -> {
                        evt.acceptTransferModes(TransferMode.ANY);
                        moveToIsland(finalI);
                        evt.consume();
                    })
            );
            islandGuiList.get(i).getContentOnIsland().forEach(
                    iv -> iv.setOnDragOver(event -> {
                        event.acceptTransferModes(TransferMode.ANY);
                        event.consume();
                    }));
        }

        for (PawnColor pawnColor : PawnColor.values()) {
            hallMap.get(pawnColor).setOnDragDropped(evt -> {
                evt.acceptTransferModes(TransferMode.ANY);
                moveToHall();
                evt.consume();
            });
            hallMap.get(pawnColor).setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            });
        }
    }

    /**
     * Choose a color to move
     */
    private void chooseMoveColor(PawnColor pawnColor) {
        this.selectedColor = pawnColor;
        setSelectableMoveTarget();
    }

    /**
     * Set the selectable target
     */
    private void setSelectableMoveTarget() {
        Arrays.stream(PawnColor.values()).filter(pawnColor -> selectedColor.equals(pawnColor)).forEach(pawnColor -> hallMap.get(pawnColor).setBorder(Border.stroke(Color.YELLOW)));
        islandGuiList.forEach(islandGui -> islandGui.getIsland().setEffect(new DropShadow(25, Color.YELLOW)));
    }

    /**
     * To move a student to the hall
     */
    private void moveToHall() {
        consumeEventMoveStudent(true);
        infoLabel.setText("");
        new Thread(() -> notifyObserver(obs -> obs.updateMoveStudent(selectedColor, Target.HALL, 0))).start();
    }

    /**
     * To move a student to an island
     */
    private void moveToIsland(int id) {
        consumeEventMoveStudent(true);
        infoLabel.setText("");
        new Thread(() -> notifyObserver(obs -> obs.updateMoveStudent(selectedColor, Target.ISLAND, id))).start();
    }

    private void consumeEventMoveStudent(boolean toRemoveActionListener) {
        if (toRemoveActionListener)
            schoolGuiMap.get(nickname).getEntranceViews().forEach((color, imageViews) -> imageViews.forEach(img -> {
                img.setOnDragDetected(null);
                img.setCursor(Cursor.DEFAULT);
            }));

        for (PawnColor pawnColor : PawnColor.values()) {
            hallMap.get(pawnColor).setBorder(null);
            if (toRemoveActionListener) {
                hallMap.get(pawnColor).setOnDragDropped(null);
                hallMap.get(pawnColor).setOnDragOver(null);
            }
        }

        for (IslandGui islandGui : islandGuiList) {
            islandGui.getContentOnIsland().forEach(content -> content.setEffect(SHADOW));
            if (toRemoveActionListener) {
                islandGui.getContentOnIsland().forEach(content -> content.setOnDragDropped(null));
                islandGui.getContentOnIsland().forEach(content -> content.setOnDragOver(null));
            }
        }
    }

    /**
     * To set the mother nature's movement
     *
     * @param maximumSteps that mother nature can do
     */
    public void setMotherNatureMaximumSteps(int maximumSteps) {
        consumeEventChooseCloud();
        consumeEventMoveMotherNature();
        consumeEventMoveStudent(true);

        infoLabel.setText("Choose an island to move mother nature to. [Max steps: " + maximumSteps + "]");
        ShortBoard board = resource.getBoard();
        int index = board.getMotherNaturePos();
        for (int i = 0; i < maximumSteps; i++) {
            if (index == board.getIslands().size() - 1) {
                index = 0;
            } else {
                index++;
            }
            islandGuiList.get(index).getContentOnIsland().forEach(content -> content.setCursor(Cursor.HAND));
            islandGuiList.get(index).getIsland().setEffect(new DropShadow(25, Color.YELLOW));
            int finalI = i;
            islandGuiList.get(index).getContentOnIsland().forEach(content -> content.setOnMouseClicked(evt -> motherNatureSteps(finalI + 1)));
        }
    }

    private void motherNatureSteps(int steps) {
        consumeEventMoveMotherNature();
        infoLabel.setText("");
        new Thread(() -> notifyObserver(obs -> obs.updateStepsMN(steps))).start();
    }

    private void consumeEventMoveMotherNature() {
        for (IslandGui islandGui : islandGuiList) {
            islandGui.getContentOnIsland().forEach(content -> content.setOnMouseClicked(null));
            islandGui.getContentOnIsland().forEach(content -> content.setCursor(Cursor.DEFAULT));
            islandGui.getIsland().setEffect(SHADOW);
        }
    }

    /**
     * Set selectable clouds
     */
    public void setSelectableClouds() {
        consumeEventChooseCloud();
        consumeEventMoveMotherNature();
        consumeEventMoveStudent(true);

        infoLabel.setText("Choose a cloud to pick students from!");
        for (int i = 0; i < cloudBox.getChildren().size(); i++) {
            CloudGui node = (CloudGui) cloudBox.getChildren().get(i);
            int finalI = i;
            if (!node.getCloud().isEmpty()) {
                node.getCloudView().setEffect(new DropShadow(25, Color.YELLOW));
                node.getContentView().forEach(content -> content.setOnMouseClicked(evt -> chooseCloud(finalI)));
                node.getContentView().forEach(content -> content.setCursor(Cursor.HAND));
            }
        }
    }

    /**
     * To choose a cloud
     */
    private void chooseCloud(int i) {
        consumeEventChooseCloud();
        infoLabel.setText("Wait for the other players' turn!");
        this.canPlayCharacter = false;
        new Thread(() -> notifyObserver(obs -> obs.updateCloud(i))).start();
    }

    private void consumeEventChooseCloud() {
        cloudBox.getChildren().stream().map(CloudGui.class::cast).forEach(cloud -> cloud.getContentView().forEach(content -> {
            content.setOnMouseClicked(null);
            content.setCursor(Cursor.DEFAULT);
        }));
        cloudBox.getChildren().stream().map(CloudGui.class::cast).forEach(cloud -> cloud.getContentView().forEach(content -> content.setEffect(new DropShadow(2, Color.BLACK))));
    }

    /**
     * To ask a color to the player (by effect of a character)
     */
    public void askColor() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choose_color_character.fxml"));

        Parent root;
        try {
            root = loader.load();
            stage.setScene(new Scene(root));
            ColorCharacterSceneController controller = loader.getController();
            controller.init();
            observers.forEach(controller::addObserver);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            exit(1);
        }

        stage.getIcons().add(GuiResources.characterBack);
        stage.setTitle("Choose a color for the chosen character!");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    /**
     * To ask a swap to the player (by effect of a character)
     */
    public void askSwap(int swap) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choose_swap_character.fxml"));

        Parent root;

        try {
            root = loader.load();
            stage.setScene(new Scene(root));
            SwapCharacterSceneController controller = loader.getController();
            controller.setMaxSwaps(swap);
            observers.forEach(controller::addObserver);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            exit(1);
        }

        stage.getIcons().add(GuiResources.characterBack);
        stage.setTitle("Swap!");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    /**
     * To ask an island to the player (by effect of a character)
     */
    public void askIsland() {
        infoLabel.setText("Choose an island for the chosen character");
        for (int i = 0; i < islandGuiList.size(); i++) {
            int finalI = i;
            islandGuiList.get(i).getContentOnIsland().forEach(content -> content.setOnMouseClicked(evt -> chooseIsland(finalI)));
            islandGuiList.get(i).getContentOnIsland().forEach(content -> content.setCursor(Cursor.HAND));
            islandGuiList.get(i).getIsland().setEffect(new DropShadow(50, Color.YELLOW));
        }
    }

    /**
     * To choose an island
     */
    private void chooseIsland(int islandID) {
        consumeEventMoveMotherNature();
        infoLabel.setText("");
        new Thread(() -> notifyObserver(obs -> obs.updateIslandAction(islandID))).start();

    }

    /**
     * Set the labels
     */
    public void setInfoLabel(String msg) {
        infoLabel.setText(msg);
    }

    public void setMediaPlayer(MediaPlayer player) {
        this.player = player;
    }
}
