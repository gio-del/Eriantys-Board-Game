package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Set;

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
        connection.sendMessage(new ChooseGameModeNotification(0, false));
    }

    @Override
    public void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        connection.sendMessage(new ChooseWizAndTowerColorNotification(wizardsAvailable, colorsAvailable));
    }

    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {
        connection.sendMessage(new ChooseAssistantNotification(playableAssistant));
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
    public void askColor() {
        connection.sendMessage(new ColorNotification());
    }

    @Override
    public void askIsland() {
        connection.sendMessage(new IslandNotification());
    }

    @Override
    public void askSwapList(int swap) {
        connection.sendMessage(new SwapNotification(swap));
    }

    @Override
    public void updateScreen() {
        //do nothing
    }

    @Override
    public void showError(String msg) {
        connection.sendMessage(new ErrorMessageNotification(msg));
    }

    @Override
    public void win(String winner, boolean win) {
        connection.sendMessage(new WinNotification(winner, win));
    }

    @Override
    public void showMessage(String msg) {
        connection.sendMessage(new GenericMessageNotification(msg));
    }

    @Override
    public void askConnectionInfo() {
        //do nothing
    }

    @Override
    public void injectResource(ShortModel resource) {
        //do nothing
    }

    @Override
    public void update(Notification msg) {
        connection.sendMessage(msg);
    }
}
