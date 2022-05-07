package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;

import java.util.ArrayList;
import java.util.List;

public class CloudCli {

    private final List<String> lines = new ArrayList<>();

    public CloudCli(ShortCloud shortCloud, int number){
        lines.add(number+CLISymbol.CLOUD_HEAD_START+CLISymbol.CLOUD_HEAD);
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.GREEN), CLISymbol.CLOUD_GREEN_START, CLISymbol.CLOUD_GREEN_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.RED), CLISymbol.CLOUD_RED_START, CLISymbol.CLOUD_RED_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.YELLOW), CLISymbol.CLOUD_YELLOW_START, CLISymbol.CLOUD_YELLOW_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.PINK), CLISymbol.CLOUD_PINK_START, CLISymbol.CLOUD_PINK_END));
        lines.add(stringRow(shortCloud.getStudents().getFromColor(PawnColor.BLUE), CLISymbol.CLOUD_BLUE_START, CLISymbol.CLOUD_BLUE_END));
        lines.add(CLISymbol.CLOUD_BOTTOM);
    }

    private String stringRow(int numColor, String start, String end){

        StringBuilder string = new StringBuilder();
        string.append(start).append(empties(CLISymbol.CLOUD_REPETITION));
        if(numColor == 0){
            string.append("     ");
        } else {
            string.append(numColor).append(" x ").append("@");
        }
        string.append(empties(CLISymbol.CLOUD_REPETITION));
        string.append(end);
        return string.toString();
    }

    private String empties(int rep){
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < rep; i++){
            string.append(" ");
        }
        return string.toString();
    }

    public List<String> getLines() {return lines;}
}
