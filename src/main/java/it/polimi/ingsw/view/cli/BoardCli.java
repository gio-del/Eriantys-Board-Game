package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.place.Island;

import java.util.ArrayList;
import java.util.List;

public class BoardCli {
    private final Board board;
    private final List<IslandCli> islandsCli;

    public BoardCli(Board board){
        this.board = board;
        this.islandsCli = new ArrayList<>();
    }

    public void printBoard(){
        int i;
        int j;
        List<StringBuilder> row0 = new ArrayList<>();
        List<StringBuilder> row1 = new ArrayList<>();
        StringBuilder stringMiddle = new StringBuilder();

        int islandsFirstRow;
        int numOfIslands;
        numOfIslands = board.getIslands().size();

        for(Island island: board.getIslands()){
            IslandCli islandCli = new IslandCli(island, board.getIslands().indexOf(island), board.getMotherNaturePos());
            islandsCli.add(islandCli);
        }

        if(numOfIslands % 2 == 0){
            islandsFirstRow = numOfIslands / 2;
            for(j = 0; j < Constants.ISLAND_HIGH; j++){
                StringBuilder stringBuilder0 = new StringBuilder();
                StringBuilder stringBuilder1 = new StringBuilder();
                for(i = 0; i < islandsFirstRow; i++){
                    stringBuilder0.append(islandsCli.get(i).getLines().get(j)).append("    ");
                    stringBuilder1.append(islandsCli.get(numOfIslands - 1 - i).getLines().get(j)).append("    ");
                }
                row0.add(stringBuilder0);
                row1.add(stringBuilder1);
            }
        } else {
            islandsFirstRow = numOfIslands / 2 + 1;
            for(j = 0; j < Constants.ISLAND_HIGH; j++){
                StringBuilder stringBuilder0 = new StringBuilder();
                for(i = 0; i < islandsFirstRow; i++){
                    stringBuilder0.append(islandsCli.get(i).getLines().get(j)).append("    ");
                }
                row0.add(stringBuilder0);
            }
            for(j = 0; j < Constants.ISLAND_HIGH; j++){
                StringBuilder stringBuilder1 = new StringBuilder(empties(Constants.ISLAND_WIDTH_1 / 2));
                for(i = numOfIslands - 1; i > islandsFirstRow - 1; i--){
                    stringBuilder1.append(islandsCli.get(i).getLines().get(j)).append("    ");
                }
                row1.add(stringBuilder1);
            }
        }
        row0.forEach(System.out::println);
        stringMiddle.append(empties(Constants.ISLAND_WIDTH_1));
        stringMiddle.append(underScores((islandsFirstRow - 2) * Constants.ISLAND_WIDTH_1));
        System.out.println(stringMiddle);
        row1.forEach(System.out::println);
    }


    private String underScores(int rep){
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < rep; i++){
            string.append("_");
        }
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
}
