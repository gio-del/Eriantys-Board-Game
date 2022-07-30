package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import javafx.scene.image.Image;

import java.util.Map;
import java.util.Objects;

/**
 * Resources for the GUI
 */
public class GuiResources {

    public static final Image motherNature = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/mother_nature.png")));
    public static final Image ban = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/various/ban.png")));
    public static final Image characterBack = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/characters/CharacterBack.png")));
    public static final Image soundOn = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/various/sound_on.png")));
    public static final Image soundOff = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/various/sound_off.png")));
    //ASSISTANTS
    private static final Image turtle = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/turtle.png")));
    private static final Image elephant = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/elephant.png")));
    private static final Image dog = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/dog.png")));
    private static final Image octopus = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/octopus.png")));
    private static final Image crocodile = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/crocodile.png")));
    private static final Image fox = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/fox.png")));
    private static final Image eagle = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/eagle.png")));
    private static final Image cat = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/cat.png")));
    private static final Image ostrich = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/ostrich.png")));
    private static final Image lion = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/assistants/lion.png")));
    private static final Map<Assistant, Image> assistantToImage = Map.of(Assistant.TURTLE, turtle, Assistant.ELEPHANT, elephant, Assistant.DOG, dog, Assistant.OCTOPUS, octopus, Assistant.CROCODILE, crocodile, Assistant.FOX, fox, Assistant.EAGLE, eagle, Assistant.CAT, cat, Assistant.OSTRICH, ostrich, Assistant.LION, lion);
    //STUDENTS
    private static final Image redStudent = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/students/red_student.png")));
    private static final Image yellowStudent = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/students/yellow_student.png")));
    private static final Image blueStudent = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/students/blue_student.png")));
    private static final Image pinkStudent = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/students/pink_student.png")));
    private static final Image greenStudent = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/students/green_student.png")));
    private static final Map<PawnColor, Image> colorToImage = Map.of(PawnColor.RED, redStudent, PawnColor.YELLOW, yellowStudent, PawnColor.BLUE, blueStudent, PawnColor.PINK, pinkStudent, PawnColor.GREEN, greenStudent);
    //WIZARDS
    private static final Image king = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/wizards/king_deck.png")));
    private static final Image sage = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/wizards/sage_deck.png")));
    private static final Image sorcerer = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/wizards/sorcerer_deck.png")));
    private static final Image witch = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/wizards/witch_deck.png")));
    private static final Map<Wizard, Image> wizardToImage = Map.of(Wizard.KING, king, Wizard.SAGE, sage, Wizard.SORCERER, sorcerer, Wizard.WITCH, witch);
    //TOWER (SCHOOL)
    private static final Image blackTower = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/tower/blackTower.png")));
    private static final Image whiteTower = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/tower/whiteTower.png")));
    private static final Image greyTower = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/tower/greyTower.png")));
    private static final Map<TowerColor, Image> towerColorToImage = Map.of(TowerColor.BLACK, blackTower, TowerColor.WHITE, whiteTower, TowerColor.GREY, greyTower);
    //TOWER (ISLAND)
    private static final Image blackTowerIsland = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/tower/black_tower.png")));
    private static final Image whiteTowerIsland = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/tower/white_tower.png")));
    private static final Image greyTowerIsland = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/tower/grey_tower.png")));
    private static final Map<TowerColor, Image> towerColorToIslandImage = Map.of(TowerColor.BLACK, blackTowerIsland, TowerColor.WHITE, whiteTowerIsland, TowerColor.GREY, greyTowerIsland);
    //PROFESSOR
    private static final Image redProf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/professors/red_professor.png")));
    private static final Image yellowProf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/professors/yellow_professor.png")));
    private static final Image blueProf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/professors/blue_professor.png")));
    private static final Image pinkProf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/professors/pink_professor.png")));
    private static final Image greenProf = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/pawns/professors/green_professor.png")));
    private static final Map<PawnColor, Image> colorToProfImage = Map.of(PawnColor.RED, redProf, PawnColor.YELLOW, yellowProf, PawnColor.BLUE, blueProf, PawnColor.PINK, pinkProf, PawnColor.GREEN, greenProf);
    private static final Image firstIsland = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/islands/island1.png")));
    private static final Image secondIsland = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/islands/island2.png")));
    private static final Image thirdIsland = new Image(Objects.requireNonNull(GuiResources.class.getResourceAsStream("/images/islands/island3.png")));
    private static final Map<Integer, Image> numToIslandImage = Map.of(1, firstIsland, 2, secondIsland, 3, thirdIsland);

    private GuiResources() {
    }

    /**
     * Get a student image
     *
     * @param color the color of the student pawn
     * @return Image of the student
     */
    public static Image getStudent(PawnColor color) {
        return colorToImage.get(color);
    }

    /**
     * Get a wizard image
     *
     * @param wizard the wizard
     * @return Image of the wizard
     */
    public static Image getWizard(Wizard wizard) {
        return wizardToImage.get(wizard);
    }

    /**
     * Get a tower image
     *
     * @return Image of the tower
     */
    public static Image getTowerSchool(TowerColor color) {
        return towerColorToImage.get(color);
    }

    /**
     * Get a professor image
     *
     * @param color the color of the professor pawn
     * @return Image of the professor
     */
    public static Image getProf(PawnColor color) {
        return colorToProfImage.get(color);
    }

    /**
     * Get a tower in island
     *
     * @param color the color of the tower
     * @return Image of the tower
     */
    public static Image getTowerIsland(TowerColor color) {
        return towerColorToIslandImage.get(color);
    }


    /**
     * Get an assistant image
     *
     * @param assistant the assistant
     * @return the image of the assistant
     */
    public static Image getAssistant(Assistant assistant) {
        return assistantToImage.get(assistant);
    }

    public static Image getIsland(int island) {
        return numToIslandImage.get(island);
    }
}
