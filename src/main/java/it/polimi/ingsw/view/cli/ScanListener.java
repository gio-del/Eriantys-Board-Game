package it.polimi.ingsw.view.cli;

import java.util.Scanner;

/**
 * This class is used to scan the input from the user.
 * The listener has different request options to wait for
 * and has a default ignore request that ignores every single
 * type of input of the user when not requested
 */
public class ScanListener extends Thread {
    private final Cli cli;
    private final Scanner scanner;
    private Request request = Request.IGNORE;
    private boolean running = true;

    public ScanListener(Cli cli) {
        this.cli = cli;
        scanner = new Scanner(System.in);
    }

    /**
     * waiting for inputs form the user
     */
    @Override
    public void run() {
        while (running) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                switch (request) {
                    case IP -> {
                        request = Request.IGNORE;
                        cli.checkIP(input);
                    }
                    case NICKNAME -> {
                        request = Request.IGNORE;
                        cli.checkNickName(input);
                    }
                    case GAME_MODE -> {
                        request = Request.IGNORE;
                        cli.checkGameMode(input);
                    }
                    case WIZARD_COLOR -> {
                        request = Request.IGNORE;
                        cli.checkWizardColor(input);
                    }
                    case ASSISTANT -> {
                        request = Request.IGNORE;
                        cli.checkAssistant(input);
                    }
                    case CLOUD -> {
                        request = Request.IGNORE;
                        int charID = filter(input);
                        if (charID != -1) {
                            cli.useCharacter(charID);
                            break;
                        }
                        cli.checkCloud(converterToInt(input));
                    }
                    case MOTHER -> {
                        request = Request.IGNORE;
                        int charID = filter(input);
                        if (charID != -1) {
                            cli.useCharacter(charID);
                            break;
                        }
                        cli.checkStepsMN(converterToInt(input));
                    }
                    case STUDENT -> {
                        request = Request.IGNORE;
                        int charID = filter(input);
                        if (charID != -1) {
                            cli.useCharacter(charID);
                            break;
                        }
                        cli.checkColor(input);
                    }
                    case MOVE -> {
                        request = Request.IGNORE;
                        cli.moveToTarget(input);
                    }
                    case COLOR_ACTION -> {
                        request = Request.IGNORE;
                        cli.checkColorAction(input);
                    }
                    case ISLAND_ACTION -> {
                        request = Request.IGNORE;
                        cli.checkIslandAction(converterToInt(input));
                    }
                    case COLOR_SWAP -> {
                        request = Request.IGNORE;
                        cli.checkSwapAction(input);
                    }
                    case CONTINUE_SWAPPING -> {
                        request = Request.IGNORE;
                        cli.checkContinueSwapping(input);
                    }
                    case IGNORE -> {
                        //do nothing
                    }
                }
            }
        }
    }

    /**
     * set the request status of the listener
     * @param request
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Converts the String into a number
     *
     * @param value string to convert
     * @return -1 if conversion failed, otherwise the integer representation of the given string
     */
    public int converterToInt(String value) {
        int number;
        try {
            number = Integer.parseInt(value);
            return number;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * filters if is asked to use a character
     * @param input string to check if is ask to use a character
     * @return -1 if is not asked, id of the character to use if valid ask method
     */
    private int filter(String input) {
        int pos = cli.getSpacePos(input);
        String cmd = input.substring(0, pos);
        if (cmd.equals("use")) {
            if (pos == input.length()) {
                System.out.println("Character use not valid. The correct syntax is [use #id]. Please, insert a valid character or do the previous task");
                return -1;
            }
            int id = converterToInt(input.substring(pos + 1));
            if (id == -1) {
                System.out.println("Character id not valid! Insert a valid id, or do the previous task");
            }
            return id;
        }
        return -1;
    }

    public void stopListening() {
        running = false;
        interrupt();
    }

}
