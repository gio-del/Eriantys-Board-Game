package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;
    private Player p1;

    @BeforeEach
    void setUp() {
        Game game = new Game(2,true);
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        p1 = game.getPlayerByName("Luca");
        bank = game.getBank();
    }

    @Test
    void payTestOk() {
        assertTrue(bank.pay(p1,1));
        assertEquals(0,bank.getCashByPlayer(p1));
    }

    @Test
    void payTestFail() {
        assertFalse(bank.pay(p1,2));
        assertEquals(1, bank.getCashByPlayer(p1));
    }

    @Test
    void rewardFail() {
        for(int i=0;i<19;i++){
            assertTrue(bank.reward(p1));
        }
        assertEquals(20,bank.getCashByPlayer(p1));
        assertEquals(0,bank.getGeneralBank());
        assertFalse(bank.reward(p1));

    }
}
