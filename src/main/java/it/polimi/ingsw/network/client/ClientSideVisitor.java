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
        view.win(msg.getName(), msg.isWin());
    }

    @Override
    public void visit(CloudsNotification msg) {
        view.showClouds(msg.getCloudList());
    }

    @Override
    public void visit(MoveStudentNotification msg) {
        //TODO: check this
        view.moveStudent(msg.getNumberOfMovement(),msg.getMovableColor());
    }

    @Override
    public void visit(MoveMNNotification msg) {
        view.moveMNature(msg.getAvailableSteps());
    }

    @Override
    public void visit(ChooseCloudNotification msg) {
        view.chooseCloud(msg.getAvailableClouds());
    }

    @Override
    public void visit(ChooseGameModeNotification msg) {
        view.chooseGameMode();
    }

    @Override
    public void visit(SchoolNotification msg) {
        view.showSchools(msg.getSchool());
    }

    @Override
    public void visit(DisconnectionNotification msg) {
        view.disconnectionHandler(msg.getNickname());
    }

    @Override
    public void visit(ChooseWizAndTowerColorNotification msg) {
        view.chooseWizardAndTowerColor(msg.getAvailableWizards(),msg.getAvailableColors());
    }

    @Override
    public void visit(ChooseAssistantNotification msg) {
        view.chooseAssistant(msg.getPlayableAssistant());
    }

    @Override
    public void visit(NicknameErrorNotification msg) {
        //TODO: add a method to say that provided nickname was already used
        view.setNickname(); //this will just re-ask name
    }
}
