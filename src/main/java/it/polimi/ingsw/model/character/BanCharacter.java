package it.polimi.ingsw.model.character;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.place.Island;

import java.util.HashMap;
import java.util.Map;

@JsonTypeName("banCard")
public class BanCharacter extends CharacterCard {
    private final Map<Island,Integer> islandMapBan = new HashMap<>();
    private int numberOfBanTiles;

    public Map<Island, Integer> getIslandMapBan() {
        return islandMapBan;
    }

    public int getNumberOfBanTiles() {
        return numberOfBanTiles;
    }
}
