package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.ClientSideVisitor;

import java.util.Objects;
import java.util.Scanner;

public class ScanListener extends Thread {
    private Request request = Request.IGNORE;
    private final Cli cli;
    private Scanner scanner;
    public boolean running = true;

    public ScanListener(Cli cli){
        this.cli = cli;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run(){
        while(running){
            if(scanner.hasNextLine()){
                switch (request){
                    case IP:
                        cli.checkIP(scanner.nextLine());
                    case NICKNAME:
                        cli.checkNickName(scanner.nextLine());
                }
            }
        }
    }

    public void setRequest(Request request){
        this.request = request;
    }

    /**
     * Converts the String into a number
     * @param value
     * @return
     */
    public int converterToInt(String value){
        int number;
        try {
            number = Integer.parseInt(value);
            return number;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
