package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.view.gui.GuiResources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CloudGui extends Pane {

    ImageView cloudView;
    private ShortCloud cloud;
    private List<ImageView> studentsOn;

    public CloudGui(ShortCloud cloud) {
        this.cloud = cloud;
        this.studentsOn = new ArrayList<>();
        Image image;
        List<ImageView> imageViewList;
        if (cloud.getStudents().totalElements() == 3) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds/cloud_2_player.png")));
            imageViewList = cloudThreeStudent();
        } else {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds/cloud_3_player.png")));
            imageViewList = cloudFourStudents();
        }
        cloudView = new ImageView(image);
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
        if (!cloud.isEmpty()) {
            //FIRST STUDENT
            ImageView firstStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(0)));
            firstStudent.setLayoutX(this.getLayoutX() + 28);
            firstStudent.setLayoutY(this.getLayoutY() + 26);

            //SECOND STUDENT
            ImageView secondStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(1)));
            secondStudent.setLayoutX(this.getLayoutX() + 53);
            secondStudent.setLayoutY(this.getLayoutY() + 15);

            //THIRD STUDENT
            ImageView thirdStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(2)));
            thirdStudent.setLayoutX(this.getLayoutX() + 40);
            thirdStudent.setLayoutY(this.getLayoutY() + 50);

            //FOURTH STUDENT
            ImageView fourthStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(3)));
            fourthStudent.setLayoutX(this.getLayoutX() + 65);
            fourthStudent.setLayoutY(this.getLayoutY() + 41);

            this.studentsOn = List.of(firstStudent, secondStudent, thirdStudent, fourthStudent);
            return studentsOn;
        }
        for (ImageView student : studentsOn) {
            student.setImage(null);
        }
        return List.of();
    }

    private List<ImageView> cloudThreeStudent() {
        List<PawnColor> pawnColorList = cloud.getStudents().toList();
        if (!cloud.isEmpty()) {
            //FIRST STUDENT
            ImageView firstStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(0)));
            firstStudent.setLayoutX(this.getLayoutX() + 44);
            firstStudent.setLayoutY(this.getLayoutY() + 20);

            //SECOND STUDENT
            ImageView secondStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(1)));
            secondStudent.setLayoutX(this.getLayoutX() + 31);
            secondStudent.setLayoutY(this.getLayoutY() + 48);

            //THIRD STUDENT
            ImageView thirdStudent = new ImageView(GuiResources.getStudent(pawnColorList.get(2)));
            thirdStudent.setLayoutX(this.getLayoutX() + 62);
            thirdStudent.setLayoutY(this.getLayoutY() + 42);

            this.studentsOn = List.of(firstStudent, secondStudent, thirdStudent);
            return studentsOn;
        }
        for (ImageView student : studentsOn) {
            student.setImage(null);
        }
        return List.of();
    }

    public void setAs(ShortCloud cloud) {
        this.cloud = cloud;
        List<PawnColor> colorsOn = cloud.getStudents().toList();
        if (colorsOn.isEmpty()) {
            studentsOn.forEach(student -> student.setImage(null));
            return;
        }
        for (int i = 0; i < colorsOn.size(); i++) {
            studentsOn.get(i).setImage(GuiResources.getStudent(colorsOn.get(i)));
        }
    }

    public ShortCloud getCloud() {
        return cloud;
    }

    public ImageView getCloudView() {
        return cloudView;
    }

    public List<ImageView> getContentView() {
        List<ImageView> list = new ArrayList<>(List.of(cloudView));
        list.addAll(studentsOn);
        return list;
    }
}
