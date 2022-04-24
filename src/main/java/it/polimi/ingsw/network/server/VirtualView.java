package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.view.View;

import java.util.List;

/**
 * This class represents a fake view that is used to hide to the server the real views.
 * It allows to use MVC pattern "locally"
 */
public class VirtualView implements View {
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
    public void chooseCloud(List<ShortCloud> clouds) {
        connection.sendMessage(new ChooseCloudNotification(clouds));
    }

    @Override
    public void moveStudent(int numberOfMovement, List<PawnColor> movableColor) {
        connection.sendMessage(new MoveStudentNotification(numberOfMovement,movableColor));
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
    public void disconnectionHandler(String message) {
        //TODO: check this
        connection.sendMessage(new DisconnectionNotification());
    }

    @Override
    public void win(String winner, boolean win) {
        connection.sendMessage(new WinNotification(winner));
    }
}
