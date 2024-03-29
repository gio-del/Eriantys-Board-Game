package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.view.View;

/**
 * This class is used by the {@link ClientController} to handle a message sent by the server.
 * It implements visitor patter to dispatch {@link Notification} and perform different action on the view.
 */
public class ClientSideVisitor implements NotificationVisitor {
    private final ShortModel shortModel;
    private final View view;

    public ClientSideVisitor(View view, ShortModel shortModel) {
        this.shortModel = shortModel;
        this.view = view;
    }

    @Override
    public void visit(ModelUpdateNotification msg) {
        shortModel.update(msg.getModel());
        view.updateScreen();
    }

    @Override
    public void visit(WinNotification msg) {
        view.updateScreen();
        view.win(msg.getName(), msg.isWin());
    }

    @Override
    public void visit(MoveStudentNotification msg) {
        view.updateScreen();
        view.moveStudent(msg.getMovableColor());
    }

    @Override
    public void visit(MoveMNNotification msg) {
        view.updateScreen();
        view.moveMNature(msg.getSteps());
    }

    @Override
    public void visit(ChooseCloudNotification msg) {
        shortModel.updateClouds(msg.getAvailableClouds());
        view.updateScreen();
        view.chooseCloud(msg.getAvailableClouds());
    }

    @Override
    public void visit(ChooseGameModeNotification msg) {
        view.chooseGameMode();
    }

    @Override
    public void visit(ErrorMessageNotification msg) {
        view.showError(msg.getErrorMessage());
    }

    @Override
    public void visit(ChooseWizAndTowerColorNotification msg) {
        view.chooseWizardAndTowerColor(msg.getAvailableWizards(), msg.getAvailableColors());
    }

    @Override
    public void visit(ChooseAssistantNotification msg) {
        shortModel.setPlayableAssistant(msg.getPlayableAssistant());
        view.updateScreen();
        view.chooseAssistant(msg.getPlayableAssistant());
    }

    @Override
    public void visit(NicknameErrorNotification msg) {
        view.showMessage("Nickname already used!");
        view.setNickname(); //this will just re-ask name
    }

    @Override
    public void visit(CharacterNotification msg) {
        //do nothing
    }

    @Override
    public void visit(ColorNotification msg) {
        view.askColor();
    }

    @Override
    public void visit(IslandNotification msg) {
        view.askIsland();
    }

    @Override
    public void visit(SwapNotification msg) {
        view.askSwapList(msg.getSwap());
    }

    @Override
    public void visit(GenericMessageNotification msg) {
        view.showMessage(msg.getMessage());
    }

}
