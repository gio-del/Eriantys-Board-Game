package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;

import java.util.ArrayList;
import java.util.List;

public class SchoolSmall {
    private final List<String> lines = new ArrayList<>();

    public SchoolSmall(ShortSchool school, String name) {
        lines.add(nameBuilder(name));
        lines.add(CLISymbol.SHORT_HEADER);
        int max;
        for (PawnColor pawnColor : PawnColor.values()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CLIColor.valueOf(pawnColor.name()));
            if (school.getEntrance().getFromColor(pawnColor) != 0) {
                stringBuilder.append(school.getEntrance().getFromColor(pawnColor)).append(" x ").append("@ | ");
            } else {
                stringBuilder.append("      | ");
            }
            if (school.getHall().getFromColor(pawnColor) != 0) {
                stringBuilder.append(school.getHall().getFromColor(pawnColor)).append(" x ").append("@ |  ");
            } else {
                stringBuilder.append("      |  ");
            }

            max = school.getProfTable().getFromColor(pawnColor);
            if (max == 1) {
                stringBuilder.append(CLISymbol.FULL_PROFESSOR).append("  ");
            } else {
                stringBuilder.append(CLISymbol.EMPTY_PROFESSOR).append("  ");
            }
            lines.add(stringBuilder.toString());
        }
        lines.add(CLIColor.RESET + empties(Constants.SCHOOL_WIDTH));
        lines.add(bottomBuilder(school.getNumTower()));
    }

    private String nameBuilder(String name) {
        StringBuilder stringBuilder = new StringBuilder("SCHOOL OF: " + name);
        stringBuilder.append(empties(Constants.SCHOOL_SHORT_WIDTH - stringBuilder.length()));
        return stringBuilder.toString();
    }

    private String bottomBuilder(int numTower) {
        int i;
        StringBuilder stringBuilder = new StringBuilder("TOW: ");
        for (i = 0; i < numTower; i++) {
            stringBuilder.append(CLISymbol.TOWER).append(" ");
        }
        stringBuilder.append(empties(Constants.SCHOOL_SHORT_WIDTH - stringBuilder.length()));
        return stringBuilder.toString();
    }

    private String empties(int rep) {
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < rep; i++) {
            string.append(" ");
        }
        return string.toString();
    }

    public List<String> getLines() {
        return lines;
    }

}

