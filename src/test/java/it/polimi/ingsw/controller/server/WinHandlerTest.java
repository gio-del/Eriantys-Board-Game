package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.notification.ChooseWizAndTowerColorNotification;
import it.polimi.ingsw.network.communication.notification.Notification;
import it.polimi.ingsw.network.communication.notification.WinNotification;
import it.polimi.ingsw.network.server.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WinHandlerTest {
    private WinHandler winHandler;
    private String winner;
    private Game model;

    @BeforeEach
    void setUp() {

        Connection connection = new Connection() {

            @Override
            public void sendMessage(Notification msg) {
                if (msg instanceof WinNotification winNotification) {
                    winner = winNotification.getName(); //intercept Win messages
                }
            }

            @Override
            public void disconnect() {
                //do nothing
            }

            @Override
            public void run() {
                //do nothing
            }
        };
        GameController controller = new GameController();
        controller.addClient("Luca", connection);
        controller.addClient("Marco", connection);
        controller.init(false);
        model = controller.getGame();

        winHandler = controller.getWinHandler();

        //first player choice wizard and tower color
        Notification msg = new ChooseWizAndTowerColorNotification(Wizard.KING, TowerColor.BLACK);
        msg.setClientId(controller.getTurnManager().getPlayersOrder().get(0));
        msg.accept(controller.getVisitor());

        //second player choice wizard and tower color
        msg = new ChooseWizAndTowerColorNotification(Wizard.SORCERER, TowerColor.WHITE);
        msg.setClientId(controller.getTurnManager().getPlayersOrder().get(1));
        msg.accept(controller.getVisitor());
    }

    /**
     * Test the case of a player that has less tower than the other
     */
    @Test
    void winnerTest_1() {
        String player = model.getPlayers().get(0).getPlayerName();
        model.getPlayers().get(0).addTowerToIsland(); //now has 7 towers
        winHandler.handleWin();
        assertEquals(player, winner);
    }

    /**
     * Test the case of a player that has the same tower num of the other
     */
    @Test
    void winnerTest_2() {
        String player = model.getPlayers().get(0).getPlayerName();
        model.getPlayers().get(0).getSchool().getProfessorTable().addColor(PawnColor.GREEN); //now has 1 professors
        winHandler.handleWin();
        assertEquals(player, winner);
    }

    /**
     * Test the case of a player that has the same tower num and the same professors num of the other
     */
    @Test
    void winnerTest_3() {
        winHandler.handleWin();
        assertNotNull(winner);
    }
}
