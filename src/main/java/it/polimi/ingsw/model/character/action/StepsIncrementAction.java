package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Game;

public class StepsIncrementAction implements Action {
    private final Game game;
    private final int increment;

    public StepsIncrementAction(Game game, int increment) {
        this.game = game;
        this.increment = increment;
    }

    @Override
    public boolean apply() {
        game.setStepsIncrement(increment);
        return true;
    }
}
