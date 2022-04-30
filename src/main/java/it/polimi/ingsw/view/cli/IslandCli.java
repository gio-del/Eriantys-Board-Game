package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.Island;

import java.util.ArrayList;
import java.util.List;


public class IslandCli {
    private final List<String> lines = new ArrayList<>();


    public IslandCli(Island island, int number, int mn){
        boolean motherNature = number == mn;
        String head = headerBuilder(number, motherNature);
        lines.add(head);
        String green = stringRow(island.getStudents().getFromColor(PawnColor.GREEN), CLISymbol.GREEN_START, CLISymbol.GREEN_END);
        lines.add(green);
        String red = stringRow(island.getStudents().getFromColor(PawnColor.RED), CLISymbol.RED_START, CLISymbol.RED_END);
        lines.add(red);
        String yellow = stringRow(island.getStudents().getFromColor(PawnColor.YELLOW), CLISymbol.YELLOW_START, CLISymbol.YELLOW_END);
        lines.add(yellow);
        String pink = stringRow(island.getStudents().getFromColor(PawnColor.PINK), CLISymbol.PINK_START, CLISymbol.PINK_END);
        lines.add(pink);
        String blue = stringRow(island.getStudents().getFromColor(PawnColor.BLUE), CLISymbol.BLUE_START, CLISymbol.BLUE_END);
        lines.add(blue);
        String bottom = bottomBuilder(island);
        lines.add(bottom);
    }

    private String stringRow(int numColor, String start, String end){

        StringBuilder string = new StringBuilder();
        if(numColor >= 10){
            string.append(start).append(empties(CLISymbol.REPETITION - 1));
        } else {
            string.append(start).append(empties(CLISymbol.REPETITION));
        }
        string.append(numColor).append(" x ").append("@");
        string.append(empties(CLISymbol.REPETITION));
        string.append(end);
        return string.toString();
    }


    private String headerBuilder(int number, boolean motherNature){
        StringBuilder string = new StringBuilder();
        if(motherNature){
            if(number>=10){
                string.append(CLISymbol.HEAD_START).append(number).append(" ").append(CLISymbol.HEAD_MN).append(CLISymbol.HEAD_END);
            } else {
                string.append(CLISymbol.HEAD_START).append(number).append("  ").append(CLISymbol.HEAD_MN).append(CLISymbol.HEAD_END);
            }
        } else {
            if(number>=10){
                string.append(CLISymbol.HEAD_START).append(number).append(" ").append(CLISymbol.HEAD).append(CLISymbol.HEAD_END);

            } else {
                string.append(CLISymbol.HEAD_START).append(number).append("  ").append(CLISymbol.HEAD).append(CLISymbol.HEAD_END);
            }
        }
        return string.toString();
    }

    private String bottomBuilder(Island island){
        StringBuilder bottom = new StringBuilder();
        if(island.getTower().isPresent()){
            String color = colorTower(island);
            bottom.append(CLISymbol.BOTTOM_START).append(color).append(CLISymbol.BOTTOM_END);
        } else {
            bottom.append(CLISymbol.BOTTOM_START).append(CLISymbol.BOTTOM).append(CLISymbol.BOTTOM_END);
        }
        return bottom.toString();
    }

    private String empties(int rep){
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < rep; i++){
            string.append(" ");
        }
        return string.toString();
    }

    private String underScores(int rep){
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < rep; i++){
            string.append("_");
        }
        return string.toString();
    }

    private String colorTower(Island island){
        StringBuilder string = new StringBuilder();
        if(island.getTower().isPresent() && island.getTower().get().name().equals("WHITE")){
            string.append("_").append("WHITE(").append(island.getDimension()).append(")").append("_");
        } else if(island.getTower().isPresent() && island.getTower().get().name().equals("BLACK")){
            string.append("_").append("BLACK(").append(island.getDimension()).append(")").append("_");
        } else {
            string.append("__").append("GRAY(").append(island.getDimension()).append(")").append("_");
        }
        return string.toString();
    }

    public List<String> getLines() {return lines;}






}
