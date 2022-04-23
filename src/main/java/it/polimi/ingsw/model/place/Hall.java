package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

/**
 * Hall of the {@link School}
 */
public class Hall extends SchoolPlace {
    private final HallManager hallManager;

    public Hall(HallManager obs) {
        this.hallManager = obs;
    }

    /**
     * When a {@link Pawns} is added, foreach Pawn in the collection the observer is updated.
     * The observer manages both coins assignment (if expert mode) and professors assignment
     * @param pawns to add
     * @return {@code true} if {@code pawns} was added correctly, otherwise {@code false}.
     * When a {@link Pawns} is added the {@link HallManager} is notified
     */
    @Override
    public boolean add(Pawns pawns) {
        if(canBeMoved(pawns)) {
            for(PawnColor pawnColor: PawnColor.values()){
                int numberOfPawn = pawns.getFromColor(pawnColor);
                for(int i=0;i<numberOfPawn;i++) {
                    super.add(new Pawns(pawnColor));
                    hallManager.update(this, pawnColor);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        for(PawnColor pawnColor: PawnColor.values()){
            if(super.getPawns().getFromColor(pawnColor) + pawns.getFromColor(pawnColor) > Constants.MAX_HALL_PER_COLOR){
                return false;
            }
        }
        return true;
    }
}
