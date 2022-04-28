package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.View;

import java.util.List;

/**
 * The command line interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Cli communicates with the controller only with update() and it's a controller's job to communicate
 * with server via network
 */
public class Cli extends ClientObservable implements View {
    private final ScanListener scanListener;

    public Cli() {
        scanListener = new ScanListener(this);
        scanListener.start();
    }

    public void init(){
        printWelcome();
        askConnectionInfo();
    }

    public void askConnectionInfo() {
        System.out.println("Welcome, to start or participate in a game, insert Server ip and port (ip port):");
        setIp();
    }

    public void setIp(){
        scanListener.setRequest(Request.IP);
    }

    /**
     * Parsing of the IP
     * @param address from the input of the client
     */
    public void checkIP(String address){
        String ip; //add regex to check ip
        int port;
        int i;
        i = getSpacePos(address);
        if(i >= address.length()){
            System.out.println("INPUT NOT VALID -- Please, insert a space between ip and port");
            setIp();
            return;
        }
        ip = address.substring(0, i);
        try {
            port = Integer.parseInt(address.substring(i + 1));
            notifyObserver(observer -> observer.updateConnection(ip,port));
        } catch (NumberFormatException e) {
            System.out.println("INPUT NOT VALID -- Unable to find the port, please retry:");
            setIp();
        }
    }

    private int getSpacePos(String address) {
        int i;
        for(i = 0; i < address.length(); i++){
            if(address.charAt(i) == ' '){
                break;
            }
        }
        return i;
    }

    /**
     * To set the name of the player
     */
    @Override
    public void setNickname() {
        scanListener.setRequest(Request.NICKNAME);
        System.out.println("Insert your nickname: ");
    }

    /**
     * Check if is a valid name
     * @param nickname from input
     */
    public void checkNickName(String nickname) {
        if(nickname.length() > 0){
            notifyObserver(observer -> observer.updateNickname(nickname));
        } else {
            System.out.println("NAME not valid, please choose another name");
            scanListener.setRequest(Request.NICKNAME);
        }
    }

    @Override
    public void chooseGameMode() {
        System.out.println("Insert game mode " + Constants.GAME_MODE_AVAILABLE + " and number of player " + Constants.NUM_PLAYER_AVAILABLE + " (mode number of player): ");
        scanListener.setRequest(Request.GAME_MODE);
    }

    public void checkGameMode(String gameMode) {
        int pos = getSpacePos(gameMode);
        if(pos >= gameMode.length()){
            System.out.println("ERROR - Game mode or number of player are missing!");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        String mode = gameMode.substring(0,pos);
        if(Constants.GAME_MODE_AVAILABLE.stream().noneMatch(s -> s.equalsIgnoreCase(mode))){
            System.out.println("ERROR - Insert a valid game mode!");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        int numOfPlayer = Integer.parseInt(gameMode.substring(pos+1));
        if(Constants.NUM_PLAYER_AVAILABLE.stream().noneMatch(i -> i.equals(numOfPlayer))){
            System.out.println("ERROR - Insert a valid number of player!");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        notifyObserver(observer -> observer.updateGameModeNumPlayer(mode,numOfPlayer));
    }

    @Override
    public void chooseAssistant(List<Assistant> playableAssistant) {

    }

    @Override
    public void chooseWizardAndTowerColor(List<Wizard> wizardsAvailable, List<TowerColor> colorsAvailable) {

    }

    @Override
    public void chooseCloud(List<ShortCloud> clouds) {

    }

    @Override
    public void moveMNature(int maximumSteps) {

    }

    @Override
    public void moveStudent(int numberOfMovement, List<PawnColor> movableColor) {

    }

    @Override
    public void useCharacter(List<Character> characterNotAlreadyPlayed) {

    }

    @Override
    public void disconnectionHandler(String message) {
        System.out.println(message);
    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void win(String winner, boolean win) {

    }

    public void printWelcome(){
        System.out.println(Color.GREEN);
        System.out.println("""
                  ______      _             _            \s
                 |  ____|    (_)           | |           \s
                 | |__   _ __ _  __ _ _ __ | |_ _   _ ___\s
                 |  __| | '__| |/ _` | '_ \\| __| | | / __|
                 | |____| |  | | (_| | | | | |_| |_| \\__ \\
                 |______|_|  |_|\\__,_|_| |_|\\__|\\__, |___/
                                                 __/ |   \s
                                                |___/    \s

                 by Giovanni De Lucia, Lorenzo Battiston, Lorenzo Dell'Era""");
        System.out.println(Color.RESET);
    }
}
