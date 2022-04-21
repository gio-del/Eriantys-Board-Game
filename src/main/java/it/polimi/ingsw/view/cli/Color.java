package it.polimi.ingsw.view.cli;

public enum Color {
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
    PURPLE("\u001B[35m"),
    PURPLE_BG("\u001B[45m"),
    CYAN("\u001B[36m"),
    CYAN_BG("\u001B[46m"),
    WHITE("\u001B[37m"),
    WHITE_BG("\u001B[47m"),

    RESET("\033[0m"),
    CLEAR("\033[H\033[2J");

    private final String ansiCode;

    Color(String ansiCode) {
        this.ansiCode = ansiCode;
    }


    @Override
    public String toString() {
        return ansiCode;
    }
}