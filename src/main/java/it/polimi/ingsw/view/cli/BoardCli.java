package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.ShortBoard;
import it.polimi.ingsw.model.place.ShortIsland;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to build the cli interface of the board
 * with the islands. It changes dynamically dimension
 * based on the number of islands
 */
public class BoardCli {
    private final ShortBoard board;
    private final List<IslandCli> islandsCli;

    public BoardCli(ShortBoard board) {
        this.board = board;
        this.islandsCli = new ArrayList<>();
    }

    /**
     * Use all the information of the board to print the board
     */
    public void printBoard() {
        int i;
        int j;
        List<StringBuilder> row0 = new ArrayList<>();
        List<StringBuilder> row1 = new ArrayList<>();
        StringBuilder stringMiddle = new StringBuilder();

        int islandsFirstRow;
        int numOfIslands;
        numOfIslands = board.getIslands().size();

        printBlockIslandHeader();

        for (ShortIsland island : board.getIslands()) {
            IslandCli islandCli = new IslandCli(island, board.getIslands().indexOf(island), board.getMotherNaturePos());
            islandsCli.add(islandCli);
        }

        if (numOfIslands % 2 == 0) {
            islandsFirstRow = numOfIslands / 2;
            for (j = 0; j < Constants.ISLAND_HIGH; j++) {
                StringBuilder stringBuilder0 = new StringBuilder();
                StringBuilder stringBuilder1 = new StringBuilder();
                for (i = 0; i < islandsFirstRow; i++) {
                    stringBuilder0.append(islandsCli.get(i).getLines().get(j)).append("    ");
                    stringBuilder1.append(islandsCli.get(numOfIslands - 1 - i).getLines().get(j)).append("    ");
                }
                row0.add(stringBuilder0);
                row1.add(stringBuilder1);
            }
        } else {
            islandsFirstRow = numOfIslands / 2 + 1;
            for (j = 0; j < Constants.ISLAND_HIGH; j++) {
                StringBuilder stringBuilder0 = new StringBuilder();
                for (i = 0; i < islandsFirstRow; i++) {
                    stringBuilder0.append(islandsCli.get(i).getLines().get(j)).append("    ");
                }
                row0.add(stringBuilder0);
            }
            for (j = 0; j < Constants.ISLAND_HIGH; j++) {
                StringBuilder stringBuilder1 = new StringBuilder(empties(Constants.ISLAND_WIDTH_1 / 2));
                for (i = numOfIslands - 1; i > islandsFirstRow - 1; i--) {
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

    /**
     * prints header if present with the blocked island
     */
    private void printBlockIslandHeader() {
        StringBuilder blockedIsland = new StringBuilder("Blocked island: ");
        boolean check = false;
        for (int k = 0; k < board.getIslands().size(); k++) {
            if (board.getIslands().get(k).getBanTiles() > 0) {
                blockedIsland.append("[id: ").append(k).append(", num of tiles: ").append(board.getIslands().get(k).getBanTiles()).append("]");
                check = true;
            }
        }
        if (check) System.out.println(blockedIsland);
    }


    /**
     * create a string of underscores needed
     *
     * @param rep number of underscores
     * @return the string of underscores
     */
    private String underScores(int rep) {
        int i;
        StringBuilder string = new StringBuilder();
        for (i = 0; i < rep; i++) {
            string.append("_");
        }
        return string.toString();
    }

    /**
     * create a string of blank spaces needed
     *
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
}
