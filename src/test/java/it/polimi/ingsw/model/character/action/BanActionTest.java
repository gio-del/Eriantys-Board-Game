package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.place.Island;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BanActionTest {

    /**
     * Test the ban action card
     */
    @Test
    void banActionTest() {
        CharacterCard character = new CharacterCard();
        Island toBan = new Island();
        character.setChosenIsland(toBan);
        character.setNumberOfBanTiles(4);
        new BanAction(character).apply();
        assertEquals(1, toBan.getBanTiles());
    }
}
