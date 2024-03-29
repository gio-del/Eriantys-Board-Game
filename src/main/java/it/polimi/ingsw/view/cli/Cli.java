package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.ShortBoard;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.character.ShortCharacter;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.ShortPawns;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.ShortPlayer;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.Target;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.*;

/**
 * The command line interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Cli communicates with the controller only with update() and it's a controller's job to communicate
 * with server via network
 */
public class Cli extends ClientObservable implements View {
    private final ScanListener scanListener;
    private final List<PawnColor> swapColor = new ArrayList<>();
    private int maxSteps;
    private PawnColor chosenColor;
    private ShortModel resource;
    private String nickname;
    private int maxSwap;


    /**
     * Cli constructor, scanListener is created
     */
    public Cli() {
        scanListener = new ScanListener(this);
        scanListener.start();
    }

    /**
     * Cli shows welcome script and asks for connection
     */
    public void init() {
        clearScreen();
        printWelcome();
        askConnectionInfo();
    }

    /**
     * Ask ip and port connection and redirect to scanListener
     */
    public void askConnectionInfo() {
        System.out.println("Welcome, to start or participate in a game, insert Server ip and port (ip port):");
        setIp();
    }

    /**
     * ready to read the ip-port input
     */
    public void setIp() {
        scanListener.setRequest(Request.IP);
    }

    /**
     * Parsing of the IP
     *
     * @param address from the input of the client
     */
    public void checkIP(String address) {
        String ip;
        int port;
        int i;
        i = getSpacePos(address);
        if (i >= address.length()) {
            System.out.println("INPUT NOT VALID -- Please, insert a space between ip and port");
            setIp();
            return;
        }
        ip = address.substring(0, i);
        if (!ClientController.isValidIp(ip)) {
            System.out.println("INPUT NOT VALID -- Please, insert a valid ip");
            setIp();
            return;
        }
        port = scanListener.converterToInt(address.substring(i + 1));
        if (!ClientController.isValidPort(port)) {
            System.out.println("INPUT NOT VALID -- Please, insert a valid port");
            setIp();
        } else
            notifyObserver(observer -> observer.updateConnection(ip, port));

    }

    /**
     * get the length of the first word of a string in input
     *
     * @param string in input
     * @return size of the first word
     */
    protected int getSpacePos(String string) {
        int i;
        for (i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
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
     *
     * @param nickname from input
     */
    public void checkNickName(String nickname) {
        if (nickname.length() > 0) {
            this.nickname = nickname;
            notifyObserver(observer -> observer.updateNickname(nickname));
        } else {
            System.out.println("NAME not valid, please choose another name");
            scanListener.setRequest(Request.NICKNAME);
        }
    }

    /**
     * Shows the different available game's mode
     * and the number of players possibly playing
     */
    @Override
    public void chooseGameMode() {
        System.out.println("Insert game mode " + Constants.GAME_MODE_AVAILABLE + " and number of player " + Constants.NUM_PLAYER_AVAILABLE + " (mode number of player): ");
        scanListener.setRequest(Request.GAME_MODE);
    }

    /**
     * Checks if the string of the game mode is valid,
     * if not, set the listener to wait for a new string
     *
     * @param gameMode string in input
     */
    public void checkGameMode(String gameMode) {
        int pos = getSpacePos(gameMode);
        if (pos >= gameMode.length()) {
            System.out.println("ERROR - Game mode or number of player are missing!, retry");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        String mode = gameMode.substring(0, pos);
        if (Constants.GAME_MODE_AVAILABLE.stream().noneMatch(s -> s.equalsIgnoreCase(mode))) {
            System.out.println("ERROR - Insert a valid game mode!, retry");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        int numOfPlayer;
        try {
            numOfPlayer = Integer.parseInt(gameMode.substring(pos + 1));
        } catch (NumberFormatException e) {
            numOfPlayer = 0;
        }
        int finalNumOfPlayer = numOfPlayer;
        if (Constants.NUM_PLAYER_AVAILABLE.stream().noneMatch(i -> i.equals(finalNumOfPlayer))) {
            System.out.println("ERROR - Insert a valid number of player!, retry");
            scanListener.setRequest(Request.GAME_MODE);
            return;
        }
        System.out.println("Wait for players...");
        notifyObserver(observer -> observer.updateGameModeNumPlayer(mode, finalNumOfPlayer));
    }

    /**
     * Shows the different available wizards and tower colors
     *
     * @param wizardsAvailable not already chosen by other players
     * @param colorsAvailable  not already chosen by other players
     */
    @Override
    public void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        resource.setWizardsAvailable(wizardsAvailable);
        resource.setColorsAvailable(colorsAvailable);
        System.out.print("Choose an available wizard [");
        wizardsAvailable.forEach(o -> System.out.print(o + " "));
        System.out.print("] Choose an available TowerColor [");
        colorsAvailable.forEach(o -> System.out.print(o + " "));
        System.out.print("]");
        System.out.println();
        scanListener.setRequest(Request.WIZARD_COLOR);
    }

    /**
     * Checks if the string of the wizard and tower color is valid,
     * if not, set the listener to wait for a new string
     *
     * @param wizardAndTower in input
     */
    public void checkWizardColor(String wizardAndTower) {
        int pos = getSpacePos(wizardAndTower);
        if (pos >= wizardAndTower.length()) {
            System.out.println("ERROR - Wizard or TowerColor are missing, retry");
            scanListener.setRequest(Request.WIZARD_COLOR);
            return;
        }
        String wizard = wizardAndTower.substring(0, pos);
        Wizard wizardChosen = resource.getWizardsAvailable().stream().filter(o -> wizard.equalsIgnoreCase(o.name())).findFirst().orElse(null);
        if (wizardChosen == null) {
            System.out.println("ERROR - Wizard not available or incorrect, retry");
            scanListener.setRequest(Request.WIZARD_COLOR);
            return;
        }
        String color = wizardAndTower.substring(pos + 1);
        TowerColor towerColor = resource.getColorsAvailable().stream().filter(o -> color.equalsIgnoreCase(o.name())).findFirst().orElse(null);
        if (towerColor == null) {
            System.out.println("ERROR - Color not available or incorrect, retry");
            scanListener.setRequest(Request.WIZARD_COLOR);
            return;
        }
        notifyObserver(observer -> observer.updateWizardAndColor(wizardChosen, towerColor));
    }

    /**
     * Shows the different available assistants
     *
     * @param playableAssistant assistant in the hand the player not
     *                          already played by other in the same turn
     */
    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {
        System.out.println("Choose an assistant from the available: ");
        resource.getPlayableAssistant().forEach(o -> System.out.println(o.name().toUpperCase() + ": Value (" + o.value() + ") - Movement (" + o.movement() + ")"));
        scanListener.setRequest(Request.ASSISTANT);
    }

    /**
     * Checks if the string of the assistant is valid,
     * if not, set the listener to wait for a new string
     *
     * @param assistantName in input
     */
    public void checkAssistant(String assistantName) {
        Assistant assistant = resource.getPlayableAssistant().stream().filter(o -> assistantName.equalsIgnoreCase(o.name())).findFirst().orElse(null);
        if (assistant == null) {
            System.out.println("ERROR - Assistant not valid, retry");
            scanListener.setRequest(Request.ASSISTANT);
            return;
        }
        notifyObserver(observer -> observer.updateAssistant(assistant));
    }

    /**
     * Shows the clouds with the pawns
     *
     * @param clouds available to selected
     */
    @Override
    public void chooseCloud(List<ShortCloud> clouds) {
        System.out.println("Select the number of the cloud you want to pick from: ");
        scanListener.setRequest(Request.CLOUD);
    }

    /**
     * Checks if the number of the cloud is valid,
     * if not, set the listener to wait for a new string.
     * Valid situation: number of an existing cloud that is not empty
     *
     * @param cloudNum in input
     */
    public void checkCloud(int cloudNum) {
        if (cloudNum == -1) {
            System.out.println("ERROR - Cloud not a number, retry");
            scanListener.setRequest(Request.CLOUD);
            return;
        }
        if (cloudNum < 0 || cloudNum >= resource.getClouds().size()) {
            System.out.println("ERROR - Cloud does not exist, retry");
            scanListener.setRequest(Request.CLOUD);
            return;
        }
        if (resource.getClouds().get(cloudNum).isEmpty()) {
            System.out.println("ERROR - Chosen cloud is empty, retry");
            scanListener.setRequest(Request.CLOUD);
            return;
        }
        notifyObserver(observer -> observer.updateCloud(cloudNum));
    }

    /**
     * Shows the instruction to move mother nature and the number of maximum steps
     *
     * @param maximumSteps available for this turn for this player based on the assistant played
     */
    @Override
    public void moveMNature(int maximumSteps) {
        this.maxSteps = maximumSteps;
        System.out.println("Insert how many steps Mother Nature will do (max: " + maximumSteps + ")");
        scanListener.setRequest(Request.MOTHER);
    }

    /**
     * Checks if the number of steps is valid,
     * if not, set the listener to wait for a new string.
     *
     * @param steps in input
     */
    public void checkStepsMN(int steps) {
        if (steps == -1) {
            System.out.println("ERROR - Steps not a number, retry");
            scanListener.setRequest(Request.MOTHER);
            return;
        }
        if (steps <= 0) {
            System.out.println("ERROR - Steps should be a positive number, retry");
            scanListener.setRequest(Request.MOTHER);
            return;
        }
        if (steps > maxSteps) {
            System.out.println("ERROR - Too many steps, retry");
            scanListener.setRequest(Request.MOTHER);
            return;
        }
        notifyObserver(observer -> observer.updateStepsMN(steps));
    }

    /**
     * Shows the instruction to move a student: first select the color
     *
     * @param movableColor list of color that can be moved
     */
    @Override
    public void moveStudent(List<PawnColor> movableColor) {
        resource.setPawnsAvailable(movableColor);
        System.out.println("What color do you want to move?");
        scanListener.setRequest(Request.STUDENT);
    }

    /**
     * Checks if color is valid, shows how to move to island or hall
     *
     * @param color in input
     */
    public void checkColor(String color) {
        chosenColor = resource.getPawnsAvailable().stream().filter(o -> color.equalsIgnoreCase(o.name())).findFirst().orElse(null);
        if (chosenColor == null) {
            System.out.println("ERROR - color not available, retry");
            scanListener.setRequest(Request.STUDENT);
            return;
        }
        System.out.println("Insert destination [1 island_id] for an island and [2] for your hall: ");
        scanListener.setRequest(Request.MOVE);
    }

    /**
     * Checks id the target is valid,
     * if not, set the listener to wait for a new string.
     *
     * @param destination in input
     */
    public void moveToTarget(String destination) {
        int pos = getSpacePos(destination);
        int target = scanListener.converterToInt(destination.substring(0, pos));
        if (target == -1) {
            System.out.println("ERROR - not a number inserted, retry");
            scanListener.setRequest(Request.MOVE);
        } else {
            if (target == 1) { //ISLAND CHOICE
                if (pos == destination.length()) {
                    System.out.println("ERROR - island_id not inserted, retry.");
                    scanListener.setRequest(Request.MOVE);
                    return;
                }
                int island = scanListener.converterToInt(destination.substring(pos + 1));
                if (island == -1) {
                    System.out.println("ERROR - island_id not valid, retry.");
                    scanListener.setRequest(Request.MOVE);
                } else {
                    notifyObserver(observer -> observer.updateMoveStudent(chosenColor, Target.ISLAND, island));
                }
            } else if (target == 2) { //HALL CHOICE
                notifyObserver(observer -> observer.updateMoveStudent(chosenColor, Target.HALL, 0));
            } else {
                System.out.println("ERROR - target inserted is not valid, retry.");
                scanListener.setRequest(Request.MOVE);
            }
        }
    }

    /**
     * notify observer of character to use
     *
     * @param id character
     */
    public void useCharacter(int id) {
        notifyObserver(observer -> observer.updateUseCharacter(id));
    }

    /**
     * Shows how to select a color from the character selected
     */
    @Override
    public void askColor() {
        System.out.println("Type a color to execute the action of the character you chose:");
        scanListener.setRequest(Request.COLOR_ACTION);
    }

    /**
     * Check if the color is available for the character used,
     * if not, set the listener to wait for a new string.
     *
     * @param color in input
     */
    public void checkColorAction(String color) {
        try {
            PawnColor chosen = PawnColor.valueOf(color.toUpperCase());
            notifyObserver(observer -> observer.updateColorAction(chosen));
        } catch (IllegalArgumentException e) {
            System.out.println("Not a color, retry.");
            scanListener.setRequest(Request.COLOR_ACTION);
        }
    }

    /**
     * Shows how to select an island from the character selected
     */
    @Override
    public void askIsland() {
        System.out.println("Type a number of island to execute the action of the character you chose: ");
        scanListener.setRequest(Request.ISLAND_ACTION);
    }

    /**
     * Check if the island is available for the character used,
     * if not, set the listener to wait for a new string.
     *
     * @param island selected
     */
    public void checkIslandAction(int island) {
        if (island == -1) {
            System.out.println("Not an island, retry");
            scanListener.setRequest(Request.ISLAND_ACTION);
            return;
        }
        notifyObserver(observer -> observer.updateIslandAction(island));
    }

    /**
     * Shows how to start a swap between two colors
     *
     * @param swap number of swaps
     */
    @Override
    public void askSwapList(int swap) {
        this.maxSwap = swap;
        System.out.println("Insert a pair of color to swap! You have " + swap + " swap. Syntax [FROM TO]");
        scanListener.setRequest(Request.COLOR_SWAP);
    }

    /**
     * Checks if the swap is correctly requested,
     * if not, set the listener to wait for a new string.
     *
     * @param input string of the swap
     */
    public void checkSwapAction(String input) {
        int pos = getSpacePos(input);
        if (pos == input.length()) {
            System.out.println("Input not correct. Syntax [FROM TO]");
            scanListener.setRequest(Request.COLOR_SWAP);
        } else {
            try {
                PawnColor from = PawnColor.valueOf(input.substring(0, pos).toUpperCase());
                PawnColor to = PawnColor.valueOf(input.substring(pos + 1).toUpperCase());
                swapColor.add(from);
                swapColor.add(to);
                maxSwap--;
                if (maxSwap == 0) {
                    notifyObserver(observer -> observer.updateSwapAction(swapColor));
                    return;
                }
                System.out.println("Do you want to continue swapping pawns? [y/n] Remaining swaps: " + maxSwap);
                scanListener.setRequest(Request.CONTINUE_SWAPPING);
            } catch (IllegalArgumentException e) {
                System.out.println("Input not correct. Syntax [FROM TO]");
                scanListener.setRequest(Request.COLOR_SWAP);
            }

        }
    }

    /**
     * Checks if the player wants to swap again
     *
     * @param input response from the user
     */
    public void checkContinueSwapping(String input) {
        if (input.equals("yes") || input.equals("y")) {
            System.out.println("Insert a pair of color to swap!");
            scanListener.setRequest(Request.COLOR_SWAP);
        } else if (input.equals("no") || input.equals("n")) {
            notifyObserver(observer -> observer.updateSwapAction(swapColor));
        } else {
            System.out.println("Not correct answer, retry.");
            scanListener.setRequest(Request.CONTINUE_SWAPPING);
        }
    }

    /**
     * Shows error message
     *
     * @param msg content
     */
    @Override
    public void showError(String msg) {
        System.out.println(msg);
        System.exit(0);
    }

    /**
     * Shows the board with the islands
     *
     * @param board the board to show
     */
    private void showBoard(ShortBoard board) {
        BoardCli boardCli = new BoardCli(board);
        boardCli.printBoard();
        System.out.println();
    }

    /**
     * Shows the clouds above the board
     *
     * @param clouds during the game
     */
    public void showClouds(List<ShortCloud> clouds) {
        List<StringBuilder> lines = new ArrayList<>();
        List<CloudCli> cloudsCli = new ArrayList<>();
        for (ShortCloud shortCloud : clouds) {
            CloudCli cloudCli = new CloudCli(shortCloud, clouds.indexOf(shortCloud));
            cloudsCli.add(cloudCli);
        }
        for (int i = 0; i < Constants.ISLAND_HIGH; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (CloudCli cloudCli : cloudsCli) {
                stringBuilder.append(cloudCli.getLines().get(i)).append("   ");
            }
            lines.add(stringBuilder);
        }
        System.out.println("CLOUDS:");
        lines.forEach(System.out::println);
    }

    /**
     * Used to refresh the information on the display
     */
    @Override
    public void updateScreen() {
        Map<ShortPlayer, ShortSchool> otherSchools = new HashMap<>();
        Map<ShortPlayer, ShortSchool> ownerSchool = new HashMap<>();
        clearScreen();
        for (Map.Entry<ShortPlayer, ShortSchool> entry : resource.getSchoolMap().entrySet()) {
            if (entry.getKey().name().equals(nickname)) {
                ownerSchool.put(entry.getKey(), entry.getValue());
            } else {
                otherSchools.put(entry.getKey(), entry.getValue());
            }
        }
        if (!ownerSchool.isEmpty()) {
            SchoolsCli schoolsCli = new SchoolsCli(otherSchools, ownerSchool, resource.getMoneyMap(), resource.getAssistantMap());
            schoolsCli.printSchools();
            System.out.println(" ");
        }
        if (resource.getBoard() != null) {
            System.out.println("BOARD:");
            showBoard(resource.getBoard());
        }

        System.out.println("Remaining students in the sack: " + resource.getStudentsInSack());

        if (resource.getClouds() != null) {
            showClouds(resource.getClouds());
        }
        if (resource.getCharacters() != null) {
            showCharacter(resource.getCharacters());
        }

    }

    /**
     * Shows the list of the character available to be used,
     * the description of the action associated and the cost
     *
     * @param characters available
     */
    private void showCharacter(List<ShortCharacter> characters) {
        System.out.println("Characters in use list. To use one of them just type \"use [id]\" during your action turn (e.g. \"use 0\" to use the first character)");
        for (int i = 0; i < characters.size(); i++) {
            ShortCharacter character = characters.get(i);
            System.out.print(CLIColor.RED + "Name[ID]: " + CLIColor.RESET + character.getName() + "[" + i + "] " + CLIColor.RESET);
            printPawns(character.getStudentsOn());
            printBanTile(character.getBanTiles());
            System.out.println(CLIColor.RED + "Cost: " + CLIColor.RESET + character.getCost() + (character.hasCoinOn() ? (CLIColor.BLUE + "(+1)" + CLIColor.RESET) : ""));
            System.out.println(CLIColor.RED + "Effect: " + CLIColor.RESET + character.getDescription());
            System.out.print(CLIColor.RESET);
        }
    }

    /**
     * Prints the pawns available in the card of the character
     *
     * @param pawns associated with the character
     */
    private void printPawns(ShortPawns pawns) {
        StringBuilder builder = new StringBuilder();
        if (pawns != null) {
            for (PawnColor pawnColor : PawnColor.values()) {
                int num = pawns.getFromColor(pawnColor);
                if (num != 0) {
                    builder.append(num).append(" x ").append(CLIColor.valueOf(pawnColor.name())).append(CLISymbol.FULL_CIRCLE).append(CLIColor.RESET).append(" ");
                }
            }
        }
        System.out.print(builder);
    }

    /**
     * Prints the number of bans available for grandma
     *
     * @param banTiles the number of bain tiles on the card
     */
    private void printBanTile(int banTiles) {
        if (banTiles > 0) {
            System.out.print("Remaining ban tiles: " + banTiles);
        }
        System.out.println();
    }

    /**
     * clears the screen
     */
    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
        System.out.println(CLIColor.CLEAR);
        System.out.flush();
    }

    /**
     * shows win screen
     *
     * @param winner of the game
     * @param win    if is the player that wins
     */
    @Override
    public void win(String winner, boolean win) {
        if (win) {
            System.out.println(CLIColor.GREEN + "YOU WON!");
        } else {
            System.out.println(CLIColor.RED + "YOU LOST! " + winner + " won");
        }
        System.out.println(CLIColor.RESET);
        scanListener.stopListening();
        System.exit(0);
    }

    /**
     * Shows general message
     *
     * @param msg to be displayed
     */
    @Override
    public void showMessage(String msg) {
        System.out.print(CLIColor.YELLOW_BG + ">Server:" + CLIColor.RESET + " ");
        System.out.println(msg);
    }

    @Override
    public void injectResource(ShortModel resource) {
        this.resource = resource;
    }

    /**
     * Prints the welcome logo
     */
    public void printWelcome() {
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
