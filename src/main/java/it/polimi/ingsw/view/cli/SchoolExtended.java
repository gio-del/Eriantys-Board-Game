package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;

import java.util.ArrayList;
import java.util.List;

public class SchoolExtended {
    private final List<String> lines = new ArrayList<>();

    public SchoolExtended(ShortSchool school, String name) {
        lines.add(nameBuilder(name));
        lines.add(CLISymbol.SCHOOL_HEADER);
        int i;
        int max;
        for (PawnColor pawnColor : PawnColor.values()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CLIColor.valueOf(pawnColor.name()));
            max = school.getEntrance().getFromColor(pawnColor);
            for (i = 0; i < max; i++) {
                stringBuilder.append(CLISymbol.FULL_CIRCLE).append(" ");
            }
            for (i = max; i < Constants.MAX_ENTRANCE; i++) {
                stringBuilder.append(CLISymbol.EMPTY_CIRCLE).append(" ");
            }
            stringBuilder.append("| ");
            max = school.getHall().getFromColor(pawnColor);
            for (i = 0; i < Constants.MAX_HALL_PER_COLOR; i++) {
                if (i < max) {
                    stringBuilder.append(CLISymbol.FULL_CIRCLE).append(" ");
                } else {
                    stringBuilder.append(CLISymbol.EMPTY_CIRCLE).append(" ");
                }
            }
            stringBuilder.append("|  ");
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
        StringBuilder stringBuilder = new StringBuilder("YOUR SCHOOL: " + name);
        stringBuilder.append(empties(Constants.SCHOOL_WIDTH - stringBuilder.length()));
        return stringBuilder.toString();
    }

    private String bottomBuilder(int numTower) {
        int i;
        StringBuilder stringBuilder = new StringBuilder("TOWER TO BE PLACED: ");
        for (i = 0; i < numTower; i++) {
            stringBuilder.append(CLISymbol.TOWER).append(" ");
        }
        stringBuilder.append(empties(Constants.SCHOOL_WIDTH - stringBuilder.length()));
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
