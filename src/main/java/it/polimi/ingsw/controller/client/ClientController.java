package it.polimi.ingsw.controller.client;

import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientSideVisitor;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.Target;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.observer.ClientObserver;
import it.polimi.ingsw.view.View;

import java.util.List;

/**
 * A controller on client's side. It instantiates a {@link Client} when connection info are provided.
 * It observes the {@link View}.
 * From Server receives message through Client and update the view through the {@link ClientSideVisitor}
 * From View receives input and send them to the server through Client.
 * It uses a {@link NotificationVisitor} to dispatch the notification sent by the Server and to perform action on the view.
 */
public class ClientController implements ClientObserver {
    private final ClientSideVisitor visitor;
    private final View view;
    private final Client client;
    private String nickname;

    public ClientController(View view) {
        this.view = view;
        client = new Client(this);
        ShortModel shortModel = new ShortModel();
        visitor = new ClientSideVisitor(view, shortModel);
        view.injectResource(shortModel);
    }

    /**
     * IP validation function
     *
     * @param ip the ip provided by the user
     * @return {@code true} if the IP is valid, {@code false} otherwise.
     */
    public static boolean isValidIp(String ip) {
        String regex = "^(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(?!$)|$)){4}$)|^localhost$";
        return ip.matches(regex);
    }

    /**
     * Port number validation function
     *
     * @param port the port inserted by the user
     * @return {@code true} if the port number is valid, {@code false} otherwise.
     */
    public static boolean isValidPort(int port) {
        return port >= 1 && port <= 65535;
    }

    /**
     * Receive messages from server and dispatch them using a {@link NotificationVisitor}
     *
     * @param msg to be dispatched
     */
    public void receiveMessage(Notification msg) {
        msg.accept(visitor);
    }

    /**
     * Receive update from the view regarding connection info
     *
     * @param ip   ip provided by the user
     * @param port port number provided by the user
     */
    @Override
    public void updateConnection(String ip, int port) {
        if (client.connect(ip, port)) {
            client.start();
            view.setNickname();
        } else {
            view.showMessage("Server not found!");
            view.askConnectionInfo();
        }
    }

    /**
     * Receive update from the view regarding the chosen nickname
     *
     * @param nickname the chosen nickname
     */
    @Override
    public void updateNickname(String nickname) {
        this.nickname = nickname;
        Notification login = new LoginNotification(nickname);
        login.setClientId(nickname);
        client.sendMessage(login);
    }

    /**
     * Receive update from the view regarding the chosen game mode and number of player
     *
     * @param mode        the chosen game mode ("Simple"/"Expert")
     * @param numOfPlayer the chosen number of player (2 or 3)
     */
    @Override
    public void updateGameModeNumPlayer(String mode, int numOfPlayer) {
        boolean isExpert = mode.equalsIgnoreCase("Expert");
        Notification chooseGameMode = new ChooseGameModeNotification(numOfPlayer, isExpert);
        chooseGameMode.setClientId(nickname);
        client.sendMessage(chooseGameMode);
    }

    /**
     * Receive update from the view regarding the chosen wizard and tower color
     *
     * @param wizard     the chosen {@link Wizard}
     * @param towerColor the chosen {@link TowerColor}
     */
    @Override
    public void updateWizardAndColor(Wizard wizard, TowerColor towerColor) {
        Notification chooseWizAndTower = new ChooseWizAndTowerColorNotification(wizard, towerColor);
        chooseWizAndTower.setClientId(nickname);
        client.sendMessage(chooseWizAndTower);
    }

    /**
     * Receive update from the view regarding the chosen assistant
     *
     * @param assistant the chose {@link Assistant}
     */
    @Override
    public void updateAssistant(Assistant assistant) {
        Notification chooseAssistantNotification = new ChooseAssistantNotification(assistant);
        chooseAssistantNotification.setClientId(nickname);
        client.sendMessage(chooseAssistantNotification);
    }

    /**
     * Receive update from the view regarding the chosen cloud.
     *
     * @param cloud the chosen cloud id
     */
    @Override
    public void updateCloud(int cloud) {
        Notification chooseCloudNotification = new ChooseCloudNotification(cloud);
        chooseCloudNotification.setClientId(nickname);
        client.sendMessage(chooseCloudNotification);
    }

    /**
     * Receive update from the view regarding the number of steps that mother nature must perform
     *
     * @param steps the chosen number of steps
     */
    @Override
    public void updateStepsMN(int steps) {
        Notification moveMNNotification = new MoveMNNotification(steps);
        moveMNNotification.setClientId(nickname);
        client.sendMessage(moveMNNotification);
    }

    /**
     * Receive update from the view regarding the move of a student
     *
     * @param color  the color of the moved student
     * @param target the {@link Target}
     * @param island the chosen island id. If {@link Target} is Hall this parameter is ignored.
     */
    @Override
    public void updateMoveStudent(PawnColor color, Target target, int island) {
        Notification moveStudentNotification = new MoveStudentNotification(color, target, island);
        moveStudentNotification.setClientId(nickname);
        client.sendMessage(moveStudentNotification);
    }

    /**
     * Receive update from the view regarding the use of a {@link CharacterCard}
     *
     * @param id the chosen character id
     */
    @Override
    public void updateUseCharacter(int id) {
        Notification useCharacterNotification = new CharacterNotification(id);
        useCharacterNotification.setClientId(nickname);
        client.sendMessage(useCharacterNotification);
    }

    /**
     * Receive update from the view regarding a chosen color. It is used when a {@link CharacterCard} needs a color as input.
     *
     * @param chosen the chosen color
     */
    @Override
    public void updateColorAction(PawnColor chosen) {
        Notification colorNotification = new ColorNotification(chosen);
        colorNotification.setClientId(nickname);
        client.sendMessage(colorNotification);
    }

    /**
     * Receive update from the view regarding a chosen island. It is used when a {@link CharacterCard} needs an island as input.
     *
     * @param island the chosen island id
     */
    @Override
    public void updateIslandAction(int island) {
        Notification islandNotification = new IslandNotification(island);
        islandNotification.setClientId(nickname);
        client.sendMessage(islandNotification);
    }

    /**
     * Receive update from the view regarding a chosen color swap. It is used when a {@link  CharacterCard} needs a color swap as input.
     *
     * @param swapColor the chosen pair of color to swap
     */
    @Override
    public void updateSwapAction(List<PawnColor> swapColor) {
        Notification swapNotification = new SwapNotification(swapColor);
        swapNotification.setClientId(nickname);
        client.sendMessage(swapNotification);
    }

    /**
     * This method is called when the connection with the server is closed due to error in networking.
     */
    public void onDisconnection() {
        String s = "Connection closed with the server. Exiting...";
        view.showError(s);
    }
}
