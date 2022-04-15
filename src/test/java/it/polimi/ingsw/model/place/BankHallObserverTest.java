package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.GameLimit;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.pawns.PawnColor.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankHallObserverTest {
    private Player p1;
    private Player p2;
    private BankHallObserver bankHallObserver;
    @BeforeEach
    void setUp() {
        bankHallObserver = new BankHallObserver();

        p1 = new Player("Giovanni", Wizard.WIZ1, TowerColor.BLACK,new GameLimit(false),bankHallObserver);
        p1.getSchool().getEntrance().addPawns(new Pawns(3,0,0,0,0));

        p2 = new Player("Lorenzo",Wizard.WIZ2,TowerColor.WHITE,new GameLimit(false),bankHallObserver);
        bankHallObserver.addPlayer(p1);
        bankHallObserver.addPlayer(p2);


    }

    @Test
    void initialMoneyTest() {
        assertEquals(Constants.MAX_NUM_OF_COINS - 2, bankHallObserver.getGeneralBank());
        assertEquals(1, bankHallObserver.getCashByPlayer(p1));
        assertEquals(1, bankHallObserver.getCashByPlayer(p2));
    }

    @Test
    void CheckMoneyTriggeredTest() {
        p1.moveFromEntranceToHall(new Pawns(GREEN));
        assertEquals(1, bankHallObserver.getCashByPlayer(p1));

        p1.moveFromEntranceToHall(new Pawns(GREEN));
        assertEquals(1, bankHallObserver.getCashByPlayer(p1));

        p1.moveFromEntranceToHall(new Pawns(GREEN));
        assertEquals(2, bankHallObserver.getCashByPlayer(p1));
        assertEquals(17,bankHallObserver.getGeneralBank());


    }
}
