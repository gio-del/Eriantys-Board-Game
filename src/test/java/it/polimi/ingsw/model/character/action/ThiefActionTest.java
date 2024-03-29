package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Sack;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThiefActionTest {
    Player p1;
    Player p2;
    List<Player> players;
    Sack sack;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        sack = game.getSack();

        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Mario", Wizard.SORCERER, TowerColor.WHITE);
        game.init();

        p1 = game.getPlayerByName("Luca");
        p2 = game.getPlayerByName("Mario");

        Pawns pawns1 = new Pawns(8, 6, 5, 3, 2);
        p1.getSchool().getHall().addPawns(pawns1);

        Pawns pawns2 = new Pawns(1, 5, 3, 6, 7);
        p2.getSchool().getHall().addPawns(pawns2);

        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

    }

    /**
     * Test thief action card.
     */
    @Test
    void thiefTest() {
        Action action = new ThiefAction(PawnColor.GREEN, players, 3, sack);

        int oldSackSize = sack.getNumberOfPawns();

        action.apply();

        assertEquals(oldSackSize + 4, sack.getNumberOfPawns());

        Pawns pawnsExpectedP1 = new Pawns(5, 6, 5, 3, 2);
        assertEquals(pawnsExpectedP1, p1.getSchool().getHall());

        Pawns pawnsExpectedP2 = new Pawns(0, 5, 3, 6, 7);
        assertEquals(pawnsExpectedP2, p2.getSchool().getHall());
    }
}
