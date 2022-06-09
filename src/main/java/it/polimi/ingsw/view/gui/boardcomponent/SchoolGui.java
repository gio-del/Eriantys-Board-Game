package it.polimi.ingsw.view.gui.boardcomponent;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.ShortPlayer;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SchoolGui {
    private final List<ImageView> towerViews;
    private final Map<PawnColor, List<ImageView>> hallViewsMap;
    private final Map<PawnColor, List<ImageView>> entranceViews;
    private final Map<PawnColor, ImageView> professorsViews;
    private final ImageView wizard;
    private ShortSchool school;
    private ShortPlayer owner;

    public SchoolGui(ShortPlayer owner, ShortSchool school) {
        this.school = school;
        this.owner = owner;
        this.towerViews = new ArrayList<>();
        this.hallViewsMap = new EnumMap<>(PawnColor.class);
        this.entranceViews = new EnumMap<>(PawnColor.class);
        this.professorsViews = new EnumMap<>(PawnColor.class);
        this.wizard = new ImageView(GuiResources.getWizard(owner.wizard()));
        wizard.setPreserveRatio(true);
        wizard.setFitHeight(150);
        refresh(owner, school);
    }

    private void initializeTowerColor() {
        towerViews.clear();
        for (int i = 0; i < school.getNumTower(); i++) {
            ImageView towerView = new ImageView(GuiResources.getTowerSchool(owner.color()));
            towerView.setPreserveRatio(true);
            towerView.setFitHeight(35);
            towerViews.add(towerView);
        }
    }

    private void initializeHall() {
        hallViewsMap.clear();
        for (PawnColor pawnColor : PawnColor.values()) {
            hallViewsMap.computeIfAbsent(pawnColor, color -> hallViewsMap.put(color, new ArrayList<>()));
        }
        List<PawnColor> colorsInHall = school.getHall().toList();
        for (PawnColor pawnColor : colorsInHall) {
            ImageView colorView = new ImageView(GuiResources.getStudent(pawnColor));
            colorView.setPreserveRatio(true);
            colorView.setFitHeight(20);
            hallViewsMap.get(pawnColor).add(colorView);
        }
    }

    private void initializeEntrance() {
        entranceViews.clear();
        List<PawnColor> colorsInEntrance = school.getEntrance().toList();
        for (PawnColor pawnColor : PawnColor.values()) {
            entranceViews.computeIfAbsent(pawnColor, color -> entranceViews.put(color, new ArrayList<>()));
        }
        for (PawnColor pawnColor : colorsInEntrance) {
            ImageView colorView = new ImageView(GuiResources.getStudent(pawnColor));
            colorView.setPreserveRatio(true);
            colorView.setFitHeight(20);
            entranceViews.get(pawnColor).add(colorView);
        }
    }

    private void initializeProfessors() {
        professorsViews.clear();
        List<PawnColor> professors = school.getProfTable().toList();
        for (PawnColor pawnColor : PawnColor.values()) {
            professorsViews.computeIfAbsent(pawnColor, color -> professorsViews.put(color, new ImageView()));
        }
        for (PawnColor pawnColor : professors) {
            ImageView colorView = new ImageView(GuiResources.getProf(pawnColor));
            colorView.setPreserveRatio(true);
            colorView.setFitHeight(25);
            professorsViews.put(pawnColor, colorView);
        }
    }

    public void refresh(ShortPlayer owner, ShortSchool school) {
        this.school = school;
        this.owner = owner;
        initializeTowerColor();
        initializeHall();
        initializeEntrance();
        initializeProfessors();
    }

    public List<ImageView> getTowerViews() {
        return towerViews;
    }

    public Map<PawnColor, List<ImageView>> getHallViewsMap() {
        return hallViewsMap;
    }

    public Map<PawnColor, List<ImageView>> getEntranceViews() {
        return entranceViews;
    }

    public Map<PawnColor, ImageView> getProfessorsViews() {
        return professorsViews;
    }

    public String getOwner() {
        return owner.name();
    }

    public ImageView getWizard() {
        return wizard;
    }
}
