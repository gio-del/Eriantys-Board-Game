package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.ShortPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchoolsCli {

    private final List<SchoolSmall> otherSchools = new ArrayList<>();
    private SchoolExtended schoolExtended;
    private List<StringBuilder> upperHalf;

    public SchoolsCli(Map<ShortPlayer, ShortSchool> otherSchools, Map<ShortPlayer, ShortSchool> ownerSchool, Map<String, Integer> moneyMap) {
        for (Map.Entry<ShortPlayer, ShortSchool> entry : otherSchools.entrySet()) {
            int coin = 0;
            if (moneyMap != null) coin = moneyMap.get(entry.getKey().name());
            SchoolSmall schoolSmall = new SchoolSmall(entry.getValue(), entry.getKey(), coin);
            this.otherSchools.add(schoolSmall);
        }
        for (Map.Entry<ShortPlayer, ShortSchool> entry : ownerSchool.entrySet()) {
            int coin = 0;
            if (moneyMap != null) coin = moneyMap.get(entry.getKey().name());
            schoolExtended = new SchoolExtended(entry.getValue(), entry.getKey(), coin);
        }
        generateParts();
    }

    private void generateParts() {
        upperHalf = new ArrayList<>();
        for (int i = 0; i < Constants.SCHOOL_HIGH; i++) {
            StringBuilder up = new StringBuilder();
            up.append(schoolExtended.getLines().get(i)).append("      ");
            for (SchoolSmall schoolSmall : otherSchools) {
                up.append(schoolSmall.getLines().get(i)).append("      ");
            }
            upperHalf.add(up);
        }
    }

    public void printSchools() {
        upperHalf.forEach(System.out::println);
    }

    public static int towerBuilder(StringBuilder builder, int numTower) {
        int size = builder.length();
        for (int i = 0; i < numTower; i++) {
            if (i != numTower - 1) {
                builder.append(CLISymbol.TOWER).append(" ");
                size+=2;
            }
            else {
                builder.append(CLISymbol.TOWER);
                size++;
            }
        }
        return size;
    }

}
