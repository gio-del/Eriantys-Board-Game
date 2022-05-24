package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.ShortPawns;
import it.polimi.ingsw.model.place.ShortIsland;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IslandGui extends Pane {
    private final HBox studentsBox;
    private final ImageView island;
    private final List<ImageView> contentOnIsland;

    public IslandGui(ShortIsland shortIsland, boolean motherNatureOn) {
        this.contentOnIsland = new ArrayList<>();

        //Island picture
        this.island = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/islands/island1.png"))));
        island.setFitWidth(150);
        island.setFitHeight(150);
        getChildren().add(island);

        this.studentsBox = new HBox();
        studentsBox.setAlignment(Pos.CENTER);
        studentsBox.setPrefWidth(island.getFitWidth());
        studentsBox.setSpacing(10);
        studentsBox.setLayoutY(130);
        studentsBox.setLayoutX(island.getLayoutX());

        refresh(shortIsland, motherNatureOn);
    }

    private void drawStudents(PawnColor color, int numOfStudents) {
        if (numOfStudents > 0) {
            ImageView imageColor = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pawns/students/" + color.name().toLowerCase() + "_student.png"))));
            imageColor.setPreserveRatio(true);
            imageColor.setFitHeight(20);
            Label numOf = new Label(numOfStudents == 1 ? "" : String.valueOf(numOfStudents));
            numOf.setFont(new Font(12));
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(imageColor, numOf);
            studentsBox.getChildren().add(vBox);
        }
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
            ImageView tower = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tower/" + shortIsland.getTower().toString().toLowerCase() + "Tower.png"))));
            tower.setPreserveRatio(true);
            tower.setFitHeight(30);
            tower.setLayoutX(island.getLayoutX() + 40);
            tower.setLayoutY(island.getLayoutY() + 40);
            Label towerNum = new Label(String.valueOf(shortIsland.getDimension()));
            towerNum.setFont(new Font(15));
            towerNum.setLayoutX(island.getLayoutX() + 50);
            towerNum.setLayoutY(island.getLayoutY() + 65);
            contentOnIsland.add(tower);
            getChildren().addAll(tower, towerNum);
        }
        //MOTHER NATURE
        if (motherNatureOn) {
            ImageView motherNature = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pawns/mother_nature.png"))));
            motherNature.setPreserveRatio(true);
            motherNature.setFitHeight(65);
            motherNature.setLayoutX(island.getLayoutX() + 80);
            motherNature.setLayoutY(island.getLayoutY() + 35);
            contentOnIsland.add(motherNature);
            getChildren().add(motherNature);
        }

        //BAN TILES
        if (shortIsland.getBanTiles() > 0) {
            ImageView banTile = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/various/ban.png"))));
            banTile.setPreserveRatio(true);
            banTile.setFitHeight(30);
            banTile.setLayoutX(island.getLayoutX() + 57);
            banTile.setLayoutY(island.getLayoutY() + 75);
            Label banNum = new Label(String.valueOf(shortIsland.getBanTiles()));
            banNum.setFont(new Font(15));
            banNum.setLayoutX(island.getLayoutX() + 67);
            banNum.setLayoutY(island.getLayoutY() + 100);
            contentOnIsland.add(banTile);
            getChildren().addAll(banTile, banNum);
        }

        //STUDENTS

        ShortPawns shortPawns = shortIsland.getStudents();
        for (PawnColor pawnColor : PawnColor.values()) {
            drawStudents(pawnColor, shortPawns.getFromColor(pawnColor));
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
