package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.List;
import java.util.Optional;

/**
 * This class rapresents the single island with pawns on it, the tower if present and the dimension
 */
public class Island implements Place {
    private int dimension;
    private final Pawns students;
    private TowerColor tower;

    /**
     * Contruct the basic island
     */
    public Island(){
        this.dimension = 1;
        this.students = new Pawns();
        this.tower = null;

    }

    /**
     * Cannot be removed
     * @param pawns to be removed
     * @return false, because a pawn cannot be removed from an island
     */
    @Override
    public boolean remove(Pawns pawns) {
        return false;
    }

    /**
     * To add some students on the island
     * @param pawns to be added
     * @return if works
     */
    @Override
    public boolean add(Pawns pawns) {
        return students.addPawns(pawns);
    }

    /**
     * If students can move in this island
     * @param pawns to be moved in this island
     * @return true, always can be
     */
    @Override
    public boolean canBeMoved(Pawns pawns) {
        return true;
    }

    /**
     * If students can be removed from this island
     * @param pawns to be removed
     * @return false, must stay here
     */
    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return false;
    }

    /**
     * To add a single pawn of a color
     * @param pawnColor to be added
     * @return true if added
     */
    public boolean add(PawnColor pawnColor) {
        return students.addColor(pawnColor);
    }

    /**
     * To calculate if there is a winner of the island during a movement of mother nature
     * @param winners list of the player with the maximum score on the island
     * @param players of the game
     * @return the towerColor added if island is conquered, otherwise null if nothing changes
     */
    public TowerColor conquerIsland(List<Player> winners, List<Player> players){
        if(winners.size() == 1){
            if(this.tower == null){
                this.tower = winners.get(0).addTowerToIsland();
                return this.tower;
            } else {
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

    /**
     *
     * @return dimension of the island
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * @return the students on the island
     */
    public Pawns getStudents() {
        return students;
    }

    /**
     *
     * @return the Towercolor if a tower is present
     */
    public Optional<TowerColor> getTower() {
        return Optional.ofNullable(tower);
    }

    /**
     * Used during a conquest of the island
     * @param towerColor of the tower to be added
     */
    public void addTower(TowerColor towerColor){
        this.tower = towerColor;
    }

    /**
     * When two adjacent islands has the same towerColor they merge and change dimension
     * @param dimension of the island to merge with
     */
    public void upgradeDimension(int dimension){ this.dimension = this.dimension + dimension;
    }

    /**
     * To print the content of the island
     * @return the string of the content
     */
    @Override
    public String toString() {
        return students + (((tower!=null)?"TOWER "+tower:" ") + dimension);
    }
}
