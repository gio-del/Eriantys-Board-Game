package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.ShortPawns;
import it.polimi.ingsw.model.place.ShortIsland;
import it.polimi.ingsw.utility.Pair;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.*;

public class IslandGui extends Pane {
    private final HBox studentsBox;
    private final ImageView island;
    private final List<ImageView> contentOnIsland;
    private final ImageView tower;
    private final Label towerNum;
    private final ImageView motherNature;
    private final ImageView banTile;
    private final Label banNum;
    private final Map<PawnColor, Pair<ImageView, Label>> colorMapStudents;
    private final Map<PawnColor, VBox> colorToContainer;

    public IslandGui(ShortIsland shortIsland, boolean motherNatureOn) {
        Random random = new Random();
        this.contentOnIsland = new ArrayList<>();

        //Island picture
        this.island = new ImageView(GuiResources.getIsland(random.nextInt(1, 4)));
        island.setFitWidth(150);
        island.setFitHeight(150);
        getChildren().add(island);

        //students box
        this.studentsBox = new HBox();
        studentsBox.setAlignment(Pos.CENTER);
        studentsBox.setPrefWidth(island.getFitWidth());
        studentsBox.setSpacing(10);
        studentsBox.setLayoutY(130);
        studentsBox.setLayoutX(island.getLayoutX());

        //tower
        this.tower = new ImageView();
        tower.setPreserveRatio(true);
        tower.setFitHeight(30);
        tower.setLayoutX(island.getLayoutX() + 40);
        tower.setLayoutY(island.getLayoutY() + 40);
        this.towerNum = new Label();
        towerNum.setFont(new Font(15));
        towerNum.setLayoutX(island.getLayoutX() + 50);
        towerNum.setLayoutY(island.getLayoutY() + 65);

        //MN
        motherNature = new ImageView();
        motherNature.setPreserveRatio(true);
        motherNature.setFitHeight(65);
        motherNature.setLayoutX(island.getLayoutX() + 80);
        motherNature.setLayoutY(island.getLayoutY() + 35);

        //ban tile
        banTile = new ImageView();
        banTile.setPreserveRatio(true);
        banTile.setFitHeight(30);
        banTile.setLayoutX(island.getLayoutX() + 57);
        banTile.setLayoutY(island.getLayoutY() + 75);
        banNum = new Label();
        banNum.setFont(new Font(15));
        banNum.setLayoutX(island.getLayoutX() + 67);
        banNum.setLayoutY(island.getLayoutY() + 100);

        colorMapStudents = new EnumMap<>(PawnColor.class);
        colorToContainer = new EnumMap<>(PawnColor.class);
        for (PawnColor pawnColor : PawnColor.values()) {
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(20);
            Label label = new Label();
            label.setFont(new Font(12));
            colorMapStudents.put(pawnColor, new Pair<>(imageView, label));
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            colorToContainer.put(pawnColor, vBox);
        }

        refresh(shortIsland, motherNatureOn);
    }

    public void refresh(ShortIsland shortIsland, boolean motherNatureOn) {

        getChildren().clear();
        contentOnIsland.clear();
        contentOnIsland.add(island);

        getChildren().add(island);

        studentsBox.getChildren().clear();
        getChildren().add(studentsBox);

        //TOWER
        if (shortIsland.getTower() != null) {
            tower.setImage(GuiResources.getTowerIsland(shortIsland.getTower()));
            towerNum.setText(String.valueOf(shortIsland.getDimension()));
            contentOnIsland.add(tower);
            getChildren().addAll(tower, towerNum);
        }

        //MOTHER NATURE
        if (motherNatureOn) {
            motherNature.setImage(GuiResources.motherNature);
            contentOnIsland.add(motherNature);
            getChildren().add(motherNature);
        }

        //BAN TILES
        if (shortIsland.getBanTiles() > 0) {
            banTile.setImage(GuiResources.ban);
            banNum.setText(String.valueOf(shortIsland.getBanTiles()));
            contentOnIsland.add(banTile);
            getChildren().addAll(banTile, banNum);
        }

        //STUDENTS
        ShortPawns shortPawns = shortIsland.getStudents();
        for (PawnColor pawnColor : PawnColor.values()) {
            drawStudents(pawnColor, shortPawns.getFromColor(pawnColor));
            contentOnIsland.add(colorMapStudents.get(pawnColor).first());
        }
    }

    private void drawStudents(PawnColor color, int numOfStudents) {
        if (numOfStudents > 0) {
            ImageView imageColor = colorMapStudents.get(color).first();
            imageColor.setImage(GuiResources.getStudent(color));
            Label numOf = colorMapStudents.get(color).second();
            numOf.setText(numOfStudents == 1 ? "" : String.valueOf(numOfStudents));

            VBox vBox = colorToContainer.get(color);
            vBox.getChildren().clear();
            vBox.getChildren().addAll(imageColor, numOf);
            studentsBox.getChildren().add(vBox);
        }
    }

    public void delete() {
        getChildren().clear();
    }

    public ImageView getIsland() {
        return island;
    }

    public List<ImageView> getContentOnIsland() {
        return contentOnIsland;
    }
}
