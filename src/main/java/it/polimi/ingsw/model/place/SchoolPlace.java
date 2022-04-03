package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

/**
 * Abstract class that represents all {@link School} places
 */
public abstract class SchoolPlace implements Place{
    private final Pawns schoolPawns;

    protected SchoolPlace() {
        this.schoolPawns = new Pawns();
    }

    public Pawns getPawns() {
        return schoolPawns;
    }

    @Override
    public boolean remove(Pawns pawns) {
        return schoolPawns.removePawns(pawns);
    }

    @Override
    public boolean add(Pawns pawns) {
        return schoolPawns.addPawns(pawns);
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return schoolPawns.canBeRemoved(pawns);
    }


}
