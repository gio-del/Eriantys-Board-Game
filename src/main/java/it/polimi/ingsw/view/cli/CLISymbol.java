package it.polimi.ingsw.view.cli;

public enum CLISymbol {
    EMPTY_CIRCLE("_"),
    FULL_CIRCLE("@"),
    EMPTY_PROFESSOR("."),
    FULL_PROFESSOR("+"),
    TOWER("#"),
    SCHOOL_HEADER("     ENTRANCE     |        HALL         | PROF ");

    private final String symbol;

    CLISymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

}
