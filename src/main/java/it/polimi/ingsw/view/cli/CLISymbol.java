package it.polimi.ingsw.view.cli;

import java.awt.*;

public enum CLISymbol {
    EMPTY_CIRCLE("_"),
    FULL_CIRCLE("@"),
    EMPTY_PROFESSOR("."),
    FULL_PROFESSOR("+"),
    TOWER("#"),
    SCHOOL_HEADER("     ENTRANCE     |        HALL         | PROF ");
    public static final String HEAD_START =  " ";
    public static final String HEAD =  "___________";
    public static final String HEAD_MN = CLIColor.CYAN + "Å_Å_Å_Å_Å_Å" + CLIColor.RESET;
    public static final String HEAD_END =  "   ";
    public static final String GREEN_START = "   /" + CLIColor.GREEN;
    public static final String RED_START =   "  / " + CLIColor.RED;
    public static final String YELLOW_START =" /  " + CLIColor.YELLOW;
    public static final String PINK_START =  " \\  " + CLIColor.PINK;
    public static final String BLUE_START =  "  \\ " + CLIColor.BLUE;
    public static final String GREEN_END = CLIColor.RESET + "\\  ";
    public static final String RED_END =   CLIColor.RESET + " \\ ";
    public static final String YELLOW_END =CLIColor.RESET + "  \\";
    public static final String PINK_END =  CLIColor.RESET + "  /";
    public static final String BLUE_END =  CLIColor.RESET + " / ";
    public static final String BOTTOM_START =  "   \\";
    public static final String BOTTOM =  "__________";
    public static final String BOTTOM_END = "_/  ";
    public static final int REPETITION = 3;


    private final String symbol;

    CLISymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
