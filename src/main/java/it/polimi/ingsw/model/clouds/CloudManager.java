package it.polimi.ingsw.model.clouds;

import it.polimi.ingsw.model.Sack;
import it.polimi.ingsw.model.pawns.Pawns;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class manages the clouds in the game.
 */
public class CloudManager {
    private final List<Cloud> clouds;
    private final int studentsOnClouds;

    public CloudManager(int nPlayer, int studentsOnClouds) {
        clouds = new ArrayList<>();
        IntStream.range(0, nPlayer).mapToObj(i -> new Cloud()).forEach(clouds::add);
        this.studentsOnClouds = studentsOnClouds;
    }

    /**
     * Get and remove the students on this cloud
     *
     * @param cloud the id of the cloud to pick from
     * @return the students on the cloud if present, null otherwise
     */
    public Pawns pickFromCloud(int cloud) {
        Cloud cloudChosen = getSpecificCloud(cloud);
        if (cloudChosen != null) return cloudChosen.getStudentsAndRemove();
        return null;
    }

    /**
     * Get a specific cloud
     *
     * @param cloud the id of the cloud
     * @return the cloud associated to that id
     */
    public Cloud getSpecificCloud(int cloud) {
        if (cloud >= clouds.size()) return null;
        return clouds.get(cloud);
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    /**
     * Fill the clouds (used at the beginning of the turn) with pawn extracted from the sack
     *
     * @param sack the sack of the game
     */
    public void fillClouds(Sack sack) {
        if (sack.isEmpty()) return;
        for (Cloud cloud : clouds) {
            cloud.fill(sack.extractListOfPawns(this.studentsOnClouds));
        }
    }
}
