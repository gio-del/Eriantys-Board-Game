package it.polimi.ingsw.model.clouds;

import it.polimi.ingsw.model.Sack;
import it.polimi.ingsw.model.pawns.Pawns;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CloudManager {
    private final List<Cloud> clouds;
    private final int studentsOnClouds;

    public CloudManager(int nPlayer, int studentsOnClouds) {
        clouds = new ArrayList<>();
        IntStream.range(0, nPlayer).mapToObj(i -> new Cloud()).forEach(clouds::add);
        this.studentsOnClouds = studentsOnClouds;
    }

    public Pawns pickFromCloud(int cloud) {
        Cloud cloudChosen = getSpecificCloud(cloud);
        if (cloudChosen != null) return cloudChosen.getStudentsAndRemove();
        return null;
    }

    public Cloud getSpecificCloud(int cloud) {
        if (cloud >= clouds.size()) return null;
        return clouds.get(cloud);
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public void fillClouds(Sack sack) {
        for (Cloud cloud : clouds) {
            cloud.fill(sack.extractListOfPawns(this.studentsOnClouds));
        }
    }
}
