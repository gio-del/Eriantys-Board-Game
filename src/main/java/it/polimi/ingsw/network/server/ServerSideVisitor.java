package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
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

    public ServerSideVisitor(Game game) {
        this.game = game;
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
    public void visit(MoveStudentNotification msg) {
        if(msg.getTarget().equals(Target.ISLAND)){
            game.moveFromEntranceToIsland(msg.getColor(),msg.getIsland());
        }
        else
            game.moveFromEntranceToHall(msg.getColor());
    }

    @Override
    public void visit(MoveMNNotification msg) {
        game.moveMotherNature(msg.getSteps(),game.getCurrentPlayer());
    }

    @Override
    public void visit(ChooseCloudNotification msg) {
        game.pickFromCloud(game.getCurrentPlayer(), msg.getChosenCloud());
    }

    @Override
    public void visit(ChooseGameModeNotification msg) {
        //do nothing
    }

    @Override
    public void visit(SchoolNotification msg) {
        //do nothing
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        //do nothing
    }

    @Override
    public void visit(ChooseWizAndTowerColorNotification msg) {
        game.addPlayer(msg.getClientID(),msg.getWizard(),msg.getTowerColor());
    }

    @Override
    public void visit(ChooseAssistantNotification msg) {
        game.playAssistant(msg.getChosenAssistant());
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
}
