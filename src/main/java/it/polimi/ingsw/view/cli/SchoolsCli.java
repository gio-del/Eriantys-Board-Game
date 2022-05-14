package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.place.ShortSchool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchoolsCli {

    private final List<SchoolSmall> otherSchools = new ArrayList<>();
    private SchoolExtended schoolExtended;
    private List<StringBuilder> upperHalf;

    public SchoolsCli(Map<String, ShortSchool> otherSchools, Map<String, ShortSchool> ownerSchool) {
        for (Map.Entry<String, ShortSchool> entry : otherSchools.entrySet()) {
            SchoolSmall schoolSmall = new SchoolSmall(entry.getValue(), entry.getKey());
            this.otherSchools.add(schoolSmall);
        }
        for (Map.Entry<String, ShortSchool> entry : ownerSchool.entrySet()) {
            schoolExtended = new SchoolExtended(entry.getValue(), entry.getKey());
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

}
