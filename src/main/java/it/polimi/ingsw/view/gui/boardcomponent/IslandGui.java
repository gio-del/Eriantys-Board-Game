package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortIsland;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.Objects;
import java.util.Random;

public class IslandGui extends Pane {
    private ImageView island;
    private ImageView tower;
    private Random random = new Random();

    public IslandGui(ShortIsland shortIsland,boolean motherNatureOn) {
        island = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/islands/island"+random.nextInt(1,4)+".png"))));
        island.setFitWidth(150);
        island.setFitHeight(150);
        getChildren().addAll(island);

        //TOWER
        tower = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tower/"+shortIsland.getTower().toString().toLowerCase()+"_tower.png"))));
        tower.setPreserveRatio(true);
        tower.setFitHeight(30);
        tower.setLayoutX(island.getLayoutX()+40);
        tower.setLayoutY(island.getLayoutY()+40);
        Label towerNum = new Label(String.valueOf(shortIsland.getDimension()));
        towerNum.setFont(new Font("System",20));
        towerNum.setLayoutX(island.getLayoutX()+42);
        towerNum.setLayoutY(island.getLayoutY()+70);

        //MOTHER NATURE
        ImageView motherNature = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pawns/mother_nature.png"))));
        motherNature.setPreserveRatio(true);
        motherNature.setFitHeight(50);
        motherNature.setLayoutX(island.getLayoutX()+80);
        motherNature.setLayoutY(island.getLayoutY()+35);
        if(!motherNatureOn) motherNature.setVisible(false);

        //BAN TILES
        //todo
        //STUDENTS
        drawStudents(PawnColor.GREEN,shortIsland.getStudents().getFromColor(PawnColor.GREEN),0);
        drawStudents(PawnColor.RED,shortIsland.getStudents().getFromColor(PawnColor.RED),30);
        drawStudents(PawnColor.YELLOW,shortIsland.getStudents().getFromColor(PawnColor.YELLOW),60);
        drawStudents(PawnColor.PINK,shortIsland.getStudents().getFromColor(PawnColor.PINK),90);
        drawStudents(PawnColor.BLUE,shortIsland.getStudents().getFromColor(PawnColor.BLUE),120);

        getChildren().addAll(tower,motherNature,towerNum);
    }
    private void drawStudents(PawnColor color, int numOfStudents, int offsetX) {
        ImageView imageColor = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pawns/students/"+color.name().toLowerCase()+"_student.png"))));
        imageColor.setPreserveRatio(true);
        imageColor.setFitHeight(20);
        imageColor.setLayoutX(island.getLayoutX()+offsetX);
        imageColor.setLayoutY(island.getLayoutY()+130);
        Label numOf = new Label(String.valueOf(numOfStudents));
        numOf.setFont(new Font("System",12));
        numOf.setLayoutX(island.getLayoutX()+offsetX+6);
        numOf.setLayoutY(island.getLayoutY()+148);

        getChildren().addAll(imageColor,numOf);

    }
}
