package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;


/**
 * Hall of the {@link School}
 */
public class Hall extends SchoolPlace {
    final HallObserver hallObserver;

    public Hall() {
        this.hallObserver = HallObserver.getInstance();
    }

    /**
     *
     * @param pawns to add
     * @return {@code true} if {@code pawns} was added correctly, otherwise {@code false}.
     * When a {@link Pawns} is added the {@link HallObserver} is notified
     */
    @Override
    public boolean add(Pawns pawns) {
        if(canBeMoved(pawns)) {
            super.add(pawns);
            for(PawnColor pawnColor: PawnColor.values()){
                if(pawns.getFromColor(pawnColor)>0) hallObserver.update(pawnColor);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        for(PawnColor pawnColor: PawnColor.values()){
            if(super.getPawns().getFromColor(pawnColor) + pawns.getFromColor(pawnColor) > Constants.MAX_STUDENT_HALL_PER_COLOR){
                return false;
            }
        }
        return true;
    }
}
