package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.model.IGame;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.*;

/**
 * This class is used by the {@link GameController} to handle a message sent by the client.
 * It implements visitor pattern to dispatch a {@link Notification} and apply different action to the model
 */
public class ServerSideVisitor implements NotificationVisitor {
    private final IGame game;

    public ServerSideVisitor(IGame game) {
        this.game = game;
    }

    @Override
    public void visit(LoginNotification msg) {
        //TODO
    }

    @Override
    public void visit(WinNotification msg) {
        //TODO
    }

    @Override
    public void visit(CloudsNotification msg) {
        //TODO
    }

    @Override
    public void visit(MoveStudentNotification msg) {
        //TODO
    }

    @Override
    public void visit(MoveMNNotification msg) {
        //TODO
    }

    @Override
    public void visit(ChooseCloudNotification msg) {
        //TODO
    }

    @Override
    public void visit(ChooseGameModeNotification msg) {
        //TODO
    }

    @Override
    public void visit(SchoolNotification msg) {
        //TODO
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        //TODO
    }

    @Override
    public void visit(ChooseWizAndTowerColorNotification msg) {
        //TODO
    }

    @Override
    public void visit(ChooseAssistantNotification msg) {
        //TODO
    }

    @Override
    public void visit(NicknameErrorNotification msg) {
        //TODO
    }
}
