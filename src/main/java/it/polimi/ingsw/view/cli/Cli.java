package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.stream.Stream;

/**
 * The command line interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Cli communicates with the controller only with update() and it's a controller's job to communicate
 * with server via network
 */
public class Cli extends ClientObservable implements View {
    private final ScanListener scanListener;
    private List<Wizard> wizardsAvailable;
    private List<TowerColor> colorsAvailable;
    private List<Assistant> playableAssistant;
    private List<ShortCloud> availableClouds;
    private int maxSteps;
    private List<PawnColor> pawnsAvailable;
    private PawnColor chosenColor;

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
            System.out.println("ERROR - Game mode or number of player are missing!, retry");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        String mode = gameMode.substring(0,pos);
        if(Constants.GAME_MODE_AVAILABLE.stream().noneMatch(s -> s.equalsIgnoreCase(mode))){
            System.out.println("ERROR - Insert a valid game mode!, retry");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        int numOfPlayer = Integer.parseInt(gameMode.substring(pos+1));
        if(Constants.NUM_PLAYER_AVAILABLE.stream().noneMatch(i -> i.equals(numOfPlayer))){
            System.out.println("ERROR - Insert a valid number of player!, retry");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        notifyObserver(observer -> observer.updateGameModeNumPlayer(mode,numOfPlayer));
    }

    @Override
    public void chooseWizardAndTowerColor(List<Wizard> wizardsAvailable, List<TowerColor> colorsAvailable) {
        this.wizardsAvailable = wizardsAvailable;
        this.colorsAvailable = colorsAvailable;
        System.out.println("Choose an available wizard");
        wizardsAvailable.forEach(System.out::println);
        System.out.println("Choose an available TowerColor");
        colorsAvailable.forEach(System.out::println);
        scanListener.setRequest(Request.WIZARD_COLOR);
    }

    public void checkWizardColor(String wizardAndTower) {
        int pos = getSpacePos(wizardAndTower);
        if(pos >= wizardAndTower.length()){
            System.out.println("ERROR - Wizard or TowerColor are missing, retry");
            scanListener.setRequest(Request.WIZARD_COLOR);
            return;
        }
        String wizard = wizardAndTower.substring(0, pos);
        Wizard wizardChosen = wizardsAvailable.stream().filter(o -> wizard.equals(o.name())).findFirst().orElse(null);
        if(wizardChosen == null){
            System.out.println("ERROR - Wizard not available or incorrect, retry");
            scanListener.setRequest(Request.WIZARD_COLOR);
            return;
        }
        String color = wizardAndTower.substring(pos + 1);
        TowerColor towerColor = colorsAvailable.stream().filter(o -> color.equals(o.name())).findFirst().orElse(null);
        if(towerColor == null){
            System.out.println("ERROR - Color not available or incorrect, retry");
            scanListener.setRequest(Request.WIZARD_COLOR);
            return;
        }
        notifyObserver(observer -> observer.updateWizardAndColor(wizardChosen, towerColor));
    }



    @Override
    public void chooseAssistant(List<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
        System.out.println("Choose an assistant from the available: ");
        this.playableAssistant.forEach(o -> System.out.println( "Name " + o.name() + "- Value " + o.value() + " - Movement " + o.movement()));
        scanListener.setRequest(Request.ASSISTANT);
    }

    public void checkAssistant(String assistantName){
        Assistant assistant = playableAssistant.stream().filter(o -> assistantName.equals(o.name())).findFirst().orElse(null);
        if(assistant == null){
            System.out.println("ERROR - Assistant not valid, retry");
            scanListener.setRequest(Request.ASSISTANT);
            return;
        }
        notifyObserver(observer -> observer.updateAssistant(assistant));
    }

    @Override
    public void chooseCloud(List<ShortCloud> clouds) {
        this.availableClouds = clouds;
        System.out.println("Select the number of the cloud you want to pick from: ");
        for(ShortCloud cloud: clouds){
            System.out.println("Cloud: " + clouds.indexOf(cloud));
            Stream.of(PawnColor.values()).forEachOrdered(color -> System.out.println(cloud.getStudents().getFromColor(color) + " " + color.name() + " students"));
        }
        scanListener.setRequest(Request.CLOUD);
    }

    public void checkCloud(int cloudNum){
        if(cloudNum == - 1){
            System.out.println("ERROR - Cloud not a number, retry");
            scanListener.setRequest(Request.CLOUD);
            return;
        }
        if(cloudNum >= availableClouds.size()){
            System.out.println("ERROR - Cloud does not exist, retry");
            scanListener.setRequest(Request.CLOUD);
            return;
        }
        notifyObserver(observer -> observer.updateCloud(cloudNum));
    }

    @Override
    public void moveMNature(int maximumSteps) {
        this.maxSteps = maximumSteps;
        System.out.println("Insert how many steps Mother Nature will do (max: " + maximumSteps + ")");
        scanListener.setRequest(Request.MOTHER);
    }

    public void checkStepsMN(int steps){
        if(steps == - 1){
            System.out.println("ERROR - Steps not a number, retry");
            scanListener.setRequest(Request.MOTHER);
            return;
        }
        if(steps >= maxSteps){
            System.out.println("ERROR - Too many steps, retry");
            scanListener.setRequest(Request.MOTHER);
            return;
        }
        notifyObserver(observer -> observer.updateStepsMN(steps));
    }

    @Override
    public void moveStudent(List<PawnColor> movableColor) {
        pawnsAvailable = movableColor;
        System.out.println("What color do you want to move?");
        scanListener.setRequest(Request.STUDENT);
    }

    public void askColor(String color){
        chosenColor = pawnsAvailable.stream().filter(o -> color.equals(o.name())).findFirst().orElse(null);
        if(chosenColor == null){
            System.out.println("ERROR - color not available, retry");
            scanListener.setRequest(Request.STUDENT);
            return;
        }
        System.out.println("Do you want to move students from entrance to hall (1) or to island (2)?");
        scanListener.setRequest(Request.MOVE);
    }

    public void moveToTarget(int target) {
        if(target == - 1){
            System.out.println("ERROR - move not a number, retry");
            scanListener.setRequest(Request.MOVE);
            return;
        } else if(target == 1 || target == 2) {
            notifyObserver(observer -> observer.updateToTarget(chosenColor, target));
        }
        System.out.println("ERROR - move not available, retry");
        scanListener.setRequest(Request.MOVE);
    }
    @Override
    public void useCharacter(List<Character> characterNotAlreadyPlayed) {

    }

    @Override
    public void disconnectionHandler(String message) {
        System.out.println(message);
    }

    @Override
    public void showSchool(ShortSchool schools) {
        System.out.println("YOUR SCHOOL\n");
        System.out.println(CLISymbol.SCHOOL_HEADER);
        int i;
        int entrance;
        int hall;
        int prof;
        for(PawnColor pawnColor: PawnColor.values()){
            StringBuilder totalString = new StringBuilder();
            totalString.append(CLIColor.valueOf(pawnColor.name()));
            entrance = schools.getEntrance().getFromColor(pawnColor);
            for(i = 0; i < entrance; i ++){
                totalString.append(CLISymbol.FULL_CIRCLE).append(" ");
            }
            for (i = entrance; i < Constants.MAX_ENTRANCE; i++){
                totalString.append(CLISymbol.EMPTY_CIRCLE).append(" ");
            }
            totalString.append("| ");
            hall = schools.getHall().getFromColor(pawnColor);
            for(i = 0; i < Constants.MAX_HALL_PER_COLOR; i++){
                if(i < hall){
                    totalString.append(CLISymbol.FULL_CIRCLE).append(" ");
                } else {
                    totalString.append(CLISymbol.EMPTY_CIRCLE).append(" ");
                }
            }
            totalString.append("|  ");
            prof = schools.getProfTable().getFromColor(pawnColor);
            if(prof == 1){
                totalString.append(CLISymbol.FULL_PROFESSOR).append(" ");
            } else {
                totalString.append(CLISymbol.EMPTY_PROFESSOR).append(" ");
            }
            System.out.println(totalString);
        }
        StringBuilder towerString = new StringBuilder(CLIColor.RESET + "\nTOWER TO BE PLACED: ");
        for(i = 0; i < schools.getNumTower(); i++){
            towerString.append(CLISymbol.TOWER).append(" ");
        }
        System.out.println(towerString);
    }

    @Override
    public void showClouds(List<ShortCloud> clouds) {

    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void win(String winner, boolean win) {
        if(win){
            System.out.println("YOU WIN!");
        } else {
            System.out.println(winner + "won");
        }
        scanListener.stopListening();
    }

    public void printWelcome(){
        System.out.println(CLIColor.GREEN);
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
        System.out.println(CLIColor.RESET);
    }




}
