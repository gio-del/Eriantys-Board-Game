package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

import java.util.Objects;

public abstract class SchoolPlace implements Place{
    private final Pawns schoolPawns;

    public SchoolPlace() {
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
    public boolean canBeMoved(Pawns pawns) {
        return true;
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return schoolPawns.canBeRemoved(pawns);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolPlace that = (SchoolPlace) o;
        return Objects.equals(schoolPawns, that.schoolPawns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolPawns);
    }
}
