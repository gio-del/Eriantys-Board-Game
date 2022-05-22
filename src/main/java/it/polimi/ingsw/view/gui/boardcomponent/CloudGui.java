package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Objects;

public class CloudGui extends Pane {

    private final ShortCloud cloud;

    public CloudGui(ShortCloud cloud) {
        this.cloud = cloud;
        //TODO fix when the cloud is empty: it must show the same image but without pawns on...

        Image image;
        List<ImageView> imageViewList;
        if (cloud.getStudents().totalElements() == 3) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds/cloud_2_player.png")));
            imageViewList = cloudThreeStudent();
        } else {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds/cloud_3_player.png")));
            imageViewList = cloudFourStudents();
        }
        ImageView cloudView = new ImageView(image);
        cloudView.setPreserveRatio(true);
        cloudView.fitHeightProperty().bind(this.heightProperty());
        cloudView.fitWidthProperty().bind(this.widthProperty());
        this.getChildren().add(cloudView);

        imageViewList.forEach(imageView -> {
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(20);
            this.getChildren().add(imageView);
        });
    }

    private List<ImageView> cloudFourStudents() {
        List<PawnColor> pawnColorList = cloud.getStudents().toList();

        //FIRST STUDENT
        ImageView firstStudent = buildImageView(pawnColorList.get(0));
        firstStudent.setLayoutX(this.getLayoutX() + 28);
        firstStudent.setLayoutY(this.getLayoutY() + 26);

        //SECOND STUDENT
        ImageView secondStudent = buildImageView(pawnColorList.get(1));
        secondStudent.setLayoutX(this.getLayoutX() + 53);
        secondStudent.setLayoutY(this.getLayoutY() + 15);

        //THIRD STUDENT
        ImageView thirdStudent = buildImageView(pawnColorList.get(2));
        thirdStudent.setLayoutX(this.getLayoutX() + 40);
        thirdStudent.setLayoutY(this.getLayoutY() + 50);

        //FOURTH STUDENT
        ImageView fourthStudent = buildImageView(pawnColorList.get(3));
        fourthStudent.setLayoutX(this.getLayoutX() + 65);
        fourthStudent.setLayoutY(this.getLayoutY() + 41);

        return List.of(firstStudent, secondStudent, thirdStudent, fourthStudent);
    }

    private List<ImageView> cloudThreeStudent() {
        List<PawnColor> pawnColorList = cloud.getStudents().toList();

        //FIRST STUDENT
        ImageView firstStudent = buildImageView(pawnColorList.get(0));
        firstStudent.setLayoutX(this.getLayoutX() + 44);
        firstStudent.setLayoutY(this.getLayoutY() + 20);

        //SECOND STUDENT
        ImageView secondStudent = buildImageView(pawnColorList.get(1));
        secondStudent.setLayoutX(this.getLayoutX() + 31);
        secondStudent.setLayoutY(this.getLayoutY() + 48);

        //THIRD STUDENT
        ImageView thirdStudent = buildImageView(pawnColorList.get(2));
        thirdStudent.setLayoutX(this.getLayoutX() + 62);
        thirdStudent.setLayoutY(this.getLayoutY() + 42);

        return List.of(firstStudent, secondStudent, thirdStudent);
    }

    private ImageView buildImageView(PawnColor pawnColor) {
        return new ImageView(new Image("/images/pawns/students/" + pawnColor.name().toLowerCase() + "_student.png"));
    }
}
