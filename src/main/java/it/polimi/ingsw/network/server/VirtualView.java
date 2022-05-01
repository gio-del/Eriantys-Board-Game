package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;

import java.util.List;

/**
 * This class represents a fake view that is used to hide to the server the real views.
 * It allows to use MVC pattern "locally"
 */
public class VirtualView implements View, Observer {
    private final Connection connection;

    public VirtualView(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void setNickname() {
        connection.sendMessage(new LoginNotification(""));
    }

    @Override
    public void chooseGameMode() {
        connection.sendMessage(new ChooseGameModeNotification(0,false));
    }

    @Override
    public void chooseWizardAndTowerColor(List<Wizard> wizardsAvailable, List<TowerColor> colorsAvailable) {
        connection.sendMessage(new ChooseWizAndTowerColorNotification(wizardsAvailable,colorsAvailable));
    }

    @Override
    public void chooseAssistant(List<Assistant> playableAssistant) {
        connection.sendMessage(new ChooseAssistantNotification(playableAssistant));
    }

    @Override
    public void showSchool(ShortSchool school) {
        //not called by the server
    }

    @Override
    public void showBoard(Board board) {
        //not called by the server
    }

    @Override
    public void showOtherSchool(ShortSchool school) {
        //not called by the server
    }

    @Override
    public void showClouds(List<ShortCloud> clouds) {
        //not called by the server
    }

    @Override
    public void chooseCloud(List<ShortCloud> clouds) {
        connection.sendMessage(new ChooseCloudNotification(clouds));
    }

    @Override
    public void moveStudent(List<PawnColor> movableColor) {
        connection.sendMessage(new MoveStudentNotification(movableColor));
    }

    @Override
    public void moveMNature(int maximumSteps) {
        connection.sendMessage(new MoveMNNotification(maximumSteps));
    }

    @Override
    public void useCharacter(List<Character> characterNotAlreadyPlayed) {
        //TODO
    }

    @Override
    public void updateScreen() {
        //TODO
    }

    @Override
    public void showDisconnection(String nickname) {
        //TODO: check this
        connection.sendMessage(new DisconnectionNotification(nickname));
    }

    @Override
    public void win(String winner, boolean win) {
        connection.sendMessage(new WinNotification(winner,win));
    }

    @Override
    public void showMessage(String msg) {
        Notification message = new GenericMessageNotification(msg);
        connection.sendMessage(message);
    }

    @Override
    public void update(Notification msg) {
        connection.sendMessage(msg);
    }
}
