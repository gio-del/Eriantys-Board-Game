package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.pawns.PawnColor;

public class ThiefAction implements Action{
    private final PawnColor colorToSteal;

    public ThiefAction(PawnColor colorToSteal) {
        this.colorToSteal = colorToSteal;
    }

    @Override
    public boolean doAction() {
        //TODO
        return false;
    }
}
