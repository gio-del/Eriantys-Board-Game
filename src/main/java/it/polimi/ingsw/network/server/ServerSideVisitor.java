package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.controller.server.TurnManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.Target;
import it.polimi.ingsw.network.communication.notification.*;

/**
 * This class is used by the {@link GameController} to handle a message sent by the client.
 * It implements visitor pattern to dispatch a {@link Notification} and apply different action to the model
 */
public class ServerSideVisitor implements NotificationVisitor {
    private final Game game;
    private final TurnManager turn;

    public ServerSideVisitor(Game game, TurnManager turn) {
        this.game = game;
        this.turn = turn;
    }


    @Override
    public void visit(ChooseWizAndTowerColorNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            game.addPlayer(msg.getSenderID(), msg.getWizard(), msg.getTowerColor());
            turn.onChosenWizAndColor(msg.getWizard(), msg.getTowerColor());
        }
    }

    @Override
    public void visit(ChooseAssistantNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            game.playAssistant(msg.getChosenAssistant());
            turn.onChosenAssistant();
        }
    }

    @Override
    public void visit(MoveStudentNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            if (msg.getTarget().equals(Target.ISLAND)) {
                int island = msg.getIsland();
                if (island < 0 || island >= game.getBoard().numberOfIslands()) {
                    turn.onIncorrectIsland();
                    return;
                }
                game.moveFromEntranceToIsland(msg.getColor(), island);
            } else
                game.moveFromEntranceToHall(msg.getColor());
            turn.onMoveStudent();
        }
    }

    @Override
    public void visit(MoveMNNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            game.moveMotherNature(msg.getSteps(), game.getCurrentPlayer());
            turn.onMoveMN();
        }
    }

    @Override
    public void visit(ChooseCloudNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            game.pickFromCloud(msg.getChosenCloud());
            turn.onChosenCloud();
        }
    }

    @Override
    public void visit(CharacterNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            int size = game.getCharacterInUse().size();
            int characterId = msg.getCharacter();
            if (characterId < 0 || characterId >= size) {
                turn.onChosenInvalidCharacter();
                return;
            }
            turn.onChosenCharacter(game.getCharacterInUse().get(characterId));
        }
    }

    @Override
    public void visit(IslandNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            int boardSize = game.getBoard().getIslands().size();
            if (msg.getIsland() >= 0 && msg.getIsland() < boardSize) {
                turn.onChooseIsland(game.getBoard().getIslands().get(msg.getIsland()));
            } else turn.onActionFailed();

        }
    }

    @Override
    public void visit(ColorNotification msg) {
        if (turn.getRequestName().equals(msg.getSenderID())) {
            turn.onChosenColor(msg.getChosen());
        }
    }

    @Override
    public void visit(NicknameErrorNotification msg) {
        //do nothing
    }

    @Override
    public void visit(EventNotification msg) {
        //do nothing
    }

    @Override
    public void visit(GameStartedNotification msg) {
        //do nothing
    }

    @Override
    public void visit(GenericMessageNotification msg) {
        //do nothing
    }

    @Override
    public void visit(LoginNotification msg) {
        //do nothing
    }

    @Override
    public void visit(WinNotification msg) {
        //do nothing
    }

    @Override
    public void visit(CloudsNotification msg) {
        //do nothing
    }

    @Override
    public void visit(SchoolNotification msg) {
        //do nothing
    }

    @Override
    public void visit(BoardNotification msg) {
        //do nothing
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        //do nothing
    }

    @Override
    public void visit(ChooseGameModeNotification msg) {
        //do nothing
    }

    @Override
    public void visit(ModelUpdateNotification msg) {
        //do nothing
    }
}
