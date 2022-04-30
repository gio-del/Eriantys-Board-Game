package it.polimi.ingsw.view.cli;

public enum CLIColor {
    BLACK("\u001B[30m"),
    BLACK_BG("\u001B[40m"),
    RED("\u001B[31m"),
    RED_BG("\u001B[41m"),
    GREEN("\u001B[32m"),
    GREEN_BG("\u001B[42m"),
    YELLOW("\u001B[33m"),
    YELLOW_BG("\u001B[43m"),
    BLUE("\u001B[34m"),
    BLUE_BG("\u001B[44m"),
    PINK("\u001B[35m"),
    PINK_BG("\u001B[45m"),
    CYAN("\u001B[36m"),
    CYAN_BG("\u001B[46m"),
    GRAY("\u001B[37m"),
    GRAY_BG("\u001B[47m"),

    RESET("\033[0m"),
    CLEAR("\033[H\033[2J");

    private final String ansiCode;

    CLIColor(String ansiCode) {
        this.ansiCode = ansiCode;
    }


    @Override
    public String toString() {
        return ansiCode;
    }
}
