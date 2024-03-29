package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.Target;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.network.server.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameControllerTest {
    private TurnManager turnManager;
    private Game game;
    private NotificationVisitor visitor;

    @BeforeEach
    void setUp() {
        GameController controller = new GameController();
        Connection connection = new Connection() { //connection stub
            @Override
            public void sendMessage(Notification msg) {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public void run() {

            }
        };
        turnManager = controller.getTurnManager();
        visitor = controller.getVisitor();
        game = controller.getGame();

        controller.addClient("Luca", connection);
        controller.addClient("Marco", connection);
        controller.init(true);
    }

    @RepeatedTest(15)
        //to deal with the randomness of character in use
    void MatchTest() {
        List<String> playersOrder = turnManager.getPlayersOrder();

        //first player choice wizard and tower color
        Notification msg = new ChooseWizAndTowerColorNotification(Wizard.KING, TowerColor.BLACK);
        msg.setClientId(playersOrder.get(0));
        msg.accept(visitor);

        //second player choice wizard and tower color
        msg = new ChooseWizAndTowerColorNotification(Wizard.SORCERER, TowerColor.WHITE);
        msg.setClientId(playersOrder.get(1));
        msg.accept(visitor);

        assertNotNull(game.getPlayerByName("Luca"));
        assertNotNull(game.getPlayerByName("Marco"));

        IntStream.range(0, 5).forEach(i -> game.getBank().reward(game.getPlayerByName("Luca")));
        IntStream.range(0, 5).forEach(i -> game.getBank().reward(game.getPlayerByName("Marco")));

        //first player choice assistant
        msg = new ChooseAssistantNotification(Assistant.LION); //lion.value = 10, this player plays second
        msg.setClientId(playersOrder.get(0));
        msg.accept(visitor);

        //second player choice assistant
        String first = playersOrder.get(1);
        String second = playersOrder.get(0);
        msg = new ChooseAssistantNotification(Assistant.TURTLE); //turtle.value = 1, this player plays first
        msg.setClientId(playersOrder.get(1));
        msg.accept(visitor);

        //now players order is updated due to assistant choice. It should be like this [playersOrder.get(1) playersOrder.get(0)]
        assertEquals(first, playersOrder.get(0));
        assertEquals(second, playersOrder.get(1));

        //first player has to move 3 students
        String playing = playersOrder.get(0);
        int oldHallSize = game.getPlayerByName(playing).getSchool().getHall().totalElements();
        msg = new MoveStudentNotification(findFirstNotZeroStudentInEntrance(playing), Target.HALL, 0); //first student
        msg.setClientId(playing);
        msg.accept(visitor);
        msg = new MoveStudentNotification(findFirstNotZeroStudentInEntrance(playing), Target.HALL, 0); //second student
        msg.setClientId(playing);
        msg.accept(visitor);
        msg = new MoveStudentNotification(findFirstNotZeroStudentInEntrance(playing), Target.HALL, 0);//third student
        msg.setClientId(playing);
        msg.accept(visitor);

        assertEquals(oldHallSize + 3, game.getPlayerByName(playing).getSchool().getHall().totalElements());

        msg = new CharacterNotification(0);
        msg.setClientId(playing);
        msg.accept(visitor);

        satisfyRequires(playing, 0);

        int old = game.getBoard().getMotherNaturePos();
        int steps = 1;

        //first player has to move MN
        msg = new MoveMNNotification(steps); //one step is always ok :)
        msg.setClientId(playing);
        msg.accept(visitor);

        assertEquals(old + 1, game.getBoard().getMotherNaturePos());

        //first player has to choose a cloud
        msg = new ChooseCloudNotification(1); //choose the cloud with cloudID = 1
        msg.setClientId(playing);
        msg.accept(visitor);

        assertEquals(playersOrder.get(1), turnManager.getRequestName());

        //second player has to move 3 students
        playing = playersOrder.get(1);
        oldHallSize = game.getPlayerByName(playing).getSchool().getHall().totalElements();
        msg = new MoveStudentNotification(findFirstNotZeroStudentInEntrance(playing), Target.HALL, 0); //first student
        msg.setClientId(playing);
        msg.accept(visitor);
        msg = new MoveStudentNotification(findFirstNotZeroStudentInEntrance(playing), Target.HALL, 0); //second student
        msg.setClientId(playing);
        msg.accept(visitor);
        msg = new MoveStudentNotification(findFirstNotZeroStudentInEntrance(playing), Target.HALL, 0);//third student
        msg.setClientId(playing);
        msg.accept(visitor);
        assertEquals(oldHallSize + 3, game.getPlayerByName(playing).getSchool().getHall().totalElements());

        msg = new CharacterNotification(1);
        msg.setClientId(playing);
        msg.accept(visitor);
        satisfyRequires(playing, 1);

        old = game.getBoard().getMotherNaturePos();
        //second player has to move MN
        msg = new MoveMNNotification(steps); //one step is always ok :)
        msg.setClientId(playing);
        msg.accept(visitor);

        if (game.getBoard().getIslands().get(1).getDimension() == 2) {
            assertEquals(old, game.getBoard().getMotherNaturePos());
        } else
            assertEquals(old + 1, game.getBoard().getMotherNaturePos());

        //second player has to choose a cloud
        msg = new ChooseCloudNotification(0); //choose the cloud with cloudID = 1
        msg.setClientId(playing);
        msg.accept(visitor);
    }

    /**
     * This private method is used to deal with randomness of the character in use.
     *
     * @param playing the name of the player
     */
    private void satisfyRequires(String playing, int characterID) {
        for (String require : game.getCharacterInUse().get(characterID).getRequires()) {
            switch (require) {
                case "island" -> {
                    Notification msg = new IslandNotification(0);
                    msg.setClientId(playing);
                    msg.accept(visitor);
                }
                case "color" -> {
                    Notification msg = new ColorNotification(PawnColor.RED);
                    msg.setClientId(playing);
                    msg.accept(visitor);
                }
                case "swap" -> {
                    Notification msg = new SwapNotification(List.of(PawnColor.RED, PawnColor.BLUE));
                    msg.setClientId(playing);
                    msg.accept(visitor);
                }
            }
        }
    }

    /**
     * This private method is used to deal with randomness of the school.
     *
     * @param name the name of the school's owner.
     * @return pawnColor of the first non-zero student in entrance.
     */
    private PawnColor findFirstNotZeroStudentInEntrance(String name) {
        Pawns entrance = game.getPlayerByName(name).getSchool().getEntrance();
        for (PawnColor pawnColor : PawnColor.values()) {
            if (entrance.getFromColor(pawnColor) > 0) return pawnColor;
        }
        return null;
    }
}
