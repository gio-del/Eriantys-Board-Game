package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.view.View;

/**
 * This class is used by the {@link ClientController} to handle a message sent by the server.
 * It implements visitor patter to dispatch {@link Notification} and perform different action on the view.
 */
public class ClientSideVisitor implements NotificationVisitor {

    private final View view;

    public ClientSideVisitor(View view) {
        this.view = view;
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
