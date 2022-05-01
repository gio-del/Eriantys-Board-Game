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
    private Player p2;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco",Wizard.SORCERER,TowerColor.WHITE);
        game.init();

        p1 = game.getPlayerByName("Luca");
        p2 = game.getPlayerByName("Marco");

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
        for(int i=0;i<18;i++){
            assertTrue(bank.reward(p1));
        }
        assertEquals(19,bank.getCashByPlayer(p1));
        assertEquals(1,bank.getCashByPlayer(p2));
        assertEquals(0,bank.getGeneralBank());
        assertFalse(bank.reward(p1));

    }
}
