package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Game;

/**
 * This action is used to let the player move mother nature a number of steps greater than the one specified on the chosen Assistant card.
 */
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
