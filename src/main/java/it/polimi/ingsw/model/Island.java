package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.List;
import java.util.Optional;

public class Island implements Place {
    private int dimension;
    private final Pawns students;
    private TowerColor tower;

    public Island(){
        this.dimension = 1;
        this.students = new Pawns();
        this.tower = null;
    }

    @Override
    public boolean remove(Pawns pawns) {
        return students.removePawns(pawns);
    }

    @Override
    public boolean add(Pawns pawns) {
        return students.addPawns(pawns);
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        return true;
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return students.canBeRemoved(pawns);
    }

    public boolean add(PawnColor pawnColor) {
        return students.addColor(pawnColor);
    }

    public TowerColor conquerIsland(List<Player> winners, List<Player> players){
        if(this.tower == null){
            if(winners.size() == 1){
                this.tower = winners.get(0).getColor();
                return this.tower;
            }
        } else {
            if(winners.size() == 1){
                if(this.tower != (winners.get(0).getColor())){
                    for(Player player : players){
                        if(this.tower == (player.getColor())){
                            player.backTowerToPlayer();
                            this.tower = winners.get(0).addTowerToIsland();
                            return this.tower;
                        }
                    }
                }
            }
        }
        return null;
    }

    public int getDimension() {
        return dimension;
    }

    public Pawns getStudents() {
        return students;
    }

    public Optional<TowerColor> getTower() {
        return Optional.ofNullable(tower);
    }

    public void addTower(TowerColor towerColor){
        this.tower = towerColor;
    }

    @Override
    public String toString() {
        return students + ((tower!=null)?"TOWER "+tower:"");
    }
}
