package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create the cloud
 */
public class CloudCli {

    private final List<String> lines = new ArrayList<>();

    /**
     * create a list of lines creating the cloud
     *
     * @param shortCloud content
     * @param number     of cloud
     */
    public CloudCli(ShortCloud shortCloud, int number) {
        lines.add(number + CLISymbol.CLOUD_HEAD_START + CLISymbol.CLOUD_HEAD);
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.GREEN), CLISymbol.CLOUD_GREEN_START, CLISymbol.CLOUD_GREEN_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.RED), CLISymbol.CLOUD_RED_START, CLISymbol.CLOUD_RED_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.YELLOW), CLISymbol.CLOUD_YELLOW_START, CLISymbol.CLOUD_YELLOW_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.PINK), CLISymbol.CLOUD_PINK_START, CLISymbol.CLOUD_PINK_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.BLUE), CLISymbol.CLOUD_BLUE_START, CLISymbol.CLOUD_BLUE_END));
        lines.add(CLISymbol.CLOUD_BOTTOM);
    }

    /**
     * Creates each string of the cloud
     *
     * @param numColor numbers of pawns of the color
     * @param start    first static part of the cloud
     * @param end      ending static part of the cloud
     * @return the strings of the cloud
     */
    private String stringRow(int numColor, String start, String end) {

        StringBuilder string = new StringBuilder();
        string.append(start).append(empties());
        if (numColor == 0) {
            string.append("     ");
        } else {
            string.append(numColor).append(" x ").append(CLISymbol.FULL_CIRCLE);
        }
        string.append(empties());
        string.append(end);
        return string.toString();
    }

    /**
     * create a string of blank spaces needed
     *
     * @return the string of blank spaces
     */
    private String empties() {
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < CLISymbol.CLOUD_REPETITION; i++) {
            string.append(" ");
        }
        return string.toString();
    }

    /**
     * @return the list of lines creating the cloud
     */
    public List<String> getLines() {
        return lines;
    }
}
