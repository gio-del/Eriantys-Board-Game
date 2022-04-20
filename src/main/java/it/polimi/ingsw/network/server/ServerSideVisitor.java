package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.*;

public class ServerSideVisitor implements NotificationVisitor {
    @Override
    public void visit(LoginNotification msg) {
        //TODO
    }

    @Override
    public void visit(WinNotification msg) {
        //TODO
    }

    @Override
    public void visit(BoardNotification msg) {
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
}
