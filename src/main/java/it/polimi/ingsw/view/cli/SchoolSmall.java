package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.ShortPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create the School of the others players,
 * a reduced and simpler school
 */
public class SchoolSmall {
    private final List<String> lines = new ArrayList<>();

    /**
     * Creates the list of lines from the school of the player
     * @param school content
     * @param player owner of the school
     * @param coin of the player
     */
    public SchoolSmall(ShortSchool school, ShortPlayer player, int coin) {
        lines.add(nameBuilder(player));
        lines.add(CLISymbol.SHORT_HEADER);
        int max;
        for (PawnColor pawnColor : PawnColor.values()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CLIColor.valueOf(pawnColor.name()));
            if (school.getEntrance().getFromColor(pawnColor) != 0) {
                stringBuilder.append(school.getEntrance().getFromColor(pawnColor)).append(" x ").append(CLISymbol.FULL_CIRCLE).append(" | ");
            } else {
                stringBuilder.append("      | ");
            }
            if (school.getHall().getFromColor(pawnColor) != 0) {
                stringBuilder.append(school.getHall().getFromColor(pawnColor)).append(" x ").append(CLISymbol.FULL_CIRCLE).append(" |  ");
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
        lines.add(CLIColor.RESET + coinBuilder(coin));
        lines.add(CLIColor.RESET + bottomBuilder(school.getNumTower()));
    }

    /**
     * creates the string with the coins information
     * @param coin number
     * @return string
     */
    private String coinBuilder(int coin) {
        StringBuilder stringBuilder = new StringBuilder();
        String coinString = "Coin: " + coin;
        String color = CLIColor.YELLOW_BG.toString() + CLIColor.BLACK;
        stringBuilder.append(color).append(coinString).append(CLIColor.RESET);
        stringBuilder.append(empties(Constants.SCHOOL_SHORT_WIDTH - stringBuilder.length() + color.length() + CLIColor.RESET.toString().length()));
        return stringBuilder.toString();
    }

    /**
     * creates the string with the player infos
     * @param player owner of the school
     * @return string
     */
    private String nameBuilder(ShortPlayer player) {
        StringBuilder stringBuilder = new StringBuilder(player.name() + "[" + player.color() + " " + player.wizard() + "]");
        stringBuilder.append(empties(Constants.SCHOOL_SHORT_WIDTH - stringBuilder.length()));
        return stringBuilder.toString();
    }

    /**
     * creates the string with the towers infos
     * @param numTower number of towers
     * @return string
     */
    private String bottomBuilder(int numTower) {
        StringBuilder stringBuilder = new StringBuilder("TOW: ");
        int size = SchoolsCli.towerBuilder(stringBuilder,numTower);
        stringBuilder.append(empties(Constants.SCHOOL_SHORT_WIDTH - size));
        return stringBuilder.toString();
    }

    /**
     * create a string of blank spaces needed
     * @param rep number of blank spaces
     * @return the string of blank spaces
     */
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

