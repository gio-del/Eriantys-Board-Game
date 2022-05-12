package it.polimi.ingsw.view.cli;

import java.util.Scanner;

public class ScanListener extends Thread {
    private Request request = Request.IGNORE;
    private final Cli cli;
    private final Scanner scanner;
    private boolean running = true;

    public ScanListener(Cli cli) {
        this.cli = cli;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while(running) {
            if(scanner.hasNextLine()) {
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
                        if(charID!=-1) {
                            cli.useCharacter(charID);
                            break;
                        }
                        cli.checkCloud(converterToInt(input));
                    }
                    case MOTHER -> {
                        request = Request.IGNORE;
                        int charID = filter(input);
                        if(charID!=-1) {
                            cli.useCharacter(charID);
                            break;
                        }
                        cli.checkStepsMN(converterToInt(input));
                    }
                    case STUDENT -> {
                        request = Request.IGNORE;
                        int charID = filter(input);
                        if(charID!=-1) {
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
                    case IGNORE -> {
                        //do nothing
                    }
                }
            }
        }
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Converts the String into a number
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

    private int filter(String input) {
        int pos = cli.getSpacePos(input);
        String cmd = input.substring(0,pos);
        if(cmd.equals("use")) {
            int id = converterToInt(input.substring(pos+1));
            if(id==-1) {
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
