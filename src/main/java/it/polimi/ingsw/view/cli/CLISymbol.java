package it.polimi.ingsw.view.cli;

public enum CLISymbol {
    EMPTY_CIRCLE("_"),
    FULL_CIRCLE("@"),
    EMPTY_PROFESSOR("."),
    FULL_PROFESSOR("+"),
    TOWER("#"),
    GREEN("GREEN  |"),
    RED("RED    |"),
    YELLOW("YELLOW |"),
    PINK("PINK   |"),
    BLUE("BLUE   |");
    public static final String SCHOOL_HEADER = "     ENTRANCE     |        HALL         | PROF";
    public static final String SHORT_HEADER = "ENTRY | HALL  | PROF";
    public static final String HEAD_START = " ";
    public static final String HEAD = "___________";
    public static final String HEAD_MN = CLIColor.GREEN + "Å_Å_Å_Å_Å_Å" + CLIColor.RESET;
    public static final String HEAD_END = "   ";
    public static final String GREEN_START = "   /" + CLIColor.GREEN;
    public static final String RED_START = "  / " + CLIColor.RED;
    public static final String YELLOW_START = " /  " + CLIColor.YELLOW;
    public static final String PINK_START = " \\  " + CLIColor.PINK;
    public static final String BLUE_START = "  \\ " + CLIColor.BLUE;
    public static final String GREEN_END = CLIColor.RESET + "\\  ";
    public static final String RED_END = CLIColor.RESET + " \\ ";
    public static final String YELLOW_END = CLIColor.RESET + "  \\";
    public static final String PINK_END = CLIColor.RESET + "  /";
    public static final String BLUE_END = CLIColor.RESET + " / ";
    public static final String BOTTOM_START = "   \\";
    public static final String BOTTOM = "__________";
    public static final String BOTTOM_END = "_/  ";
    public static final int REPETITION = 3;

    public static final int CLOUD_REPETITION = 2;


    public static final String CLOUD_HEAD = " _  _  _      ";
    public static final String CLOUD_HEAD_START = "    ";
    public static final String CLOUD_GREEN_START = "   .(" + CLIColor.GREEN;
    public static final String CLOUD_RED_START = "   ((" + CLIColor.RED;
    public static final String CLOUD_YELLOW_START = " .+( " + CLIColor.YELLOW;
    public static final String CLOUD_PINK_START = " (  ." + CLIColor.PINK;
    public static final String CLOUD_BLUE_START = "(   (" + CLIColor.BLUE;
    public static final String CLOUD_GREEN_END = CLIColor.RESET + ")_   ";
    public static final String CLOUD_RED_END = CLIColor.RESET + "))   ";
    public static final String CLOUD_YELLOW_END = CLIColor.RESET + "  )_ ";
    public static final String CLOUD_PINK_END = CLIColor.RESET + "    )";
    public static final String CLOUD_BLUE_END = CLIColor.RESET + "  ) )";
    public static final String CLOUD_BOTTOM = " `-_____________.' ";
    private final String symbol;

    CLISymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
