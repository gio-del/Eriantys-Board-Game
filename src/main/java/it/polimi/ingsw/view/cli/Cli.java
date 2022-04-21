package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.communication.notification.LoginNotification;
import it.polimi.ingsw.view.View;

import java.util.List;

/**
 * The command line interface implementation of the game
 */
public class Cli /*extends Observable*/implements View {
    private ScanListener scanListener;
    private Client client;

    public void initiate(){
        printWelcome();
    }

    public void connect() {
        scanListener = new ScanListener(this);
        scanListener.start();
        System.out.println("Welcome, to start or participate in a game, insert Server ip and port (ip port):");
        client = new Client();
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
        String ip;
        int port, i;
        for(i = 0; i < address.length(); i++){
            if(address.charAt(i) == ' '){
                break;
            }
        }
        if(i >= address.length()){
            System.out.println("INPUT NOT VALID -- Please, insert a space between ip and port");
            setIp();
            return;
        }
        ip = address.substring(0, i);
        try {
            port = Integer.parseInt(address.substring(i + 1));
        } catch (NumberFormatException e) {
            System.out.println("INPUT NOT VALID -- Unable to find the port, please retry:");
            setIp();
            return;
        }

        if(client.connect(ip, port)){
            initiate();
        } else {
            System.out.println("SERVER NOT FOUND -- Unable to find the server, please retry:");
            setIp();
            return;
        }

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
    public void checkNickName(String nickname){
        if(nickname.length() > 0){
            //TODO check name unique
            client.sendMessage(new LoginNotification(nickname));
            scanListener.setRequest(Request.IGNORE);
        } else {
            System.out.println("NAME not valid, please choose another name");
            scanListener.setRequest(Request.NICKNAME);
        }
    }

    @Override
    public void chooseAssistant(List<Assistant> playerAssistant) {

    }

    @Override
    public void chooseGameMode() {

    }

    @Override
    public void chooseTowerColor(List<TowerColor> colorsAvailable) {

    }

    @Override
    public void chooseWizard(List<Wizard> wizardsAvailable) {

    }

    @Override
    public void chooseCloud(List<Cloud> clouds) {

    }

    @Override
    public void moveMNature(int maximumSteps) {

    }

    @Override
    public void moveStudent(int numberOfMovement) {

    }

    @Override
    public void useCharacter(List<Character> characterNotAlreadyPlayed) {

    }

    @Override
    public void disconnectionHandler(String message) {

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
        //TODO: askConnectionInfo()
    }
}
