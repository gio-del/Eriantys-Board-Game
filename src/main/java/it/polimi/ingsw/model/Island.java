package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.List;
import java.util.Optional;

public class Island {
    private int dimension;
    private Pawns students;
    private TowerColor tower;

    public Island(){
        this.dimension = 1;
        this.students = new Pawns();
        this.tower = null;
    }

    public boolean addStudent(PawnColor pawnColor) {
        students.addColor(pawnColor);
        return true;
    }

    public boolean addStudent(Pawns pawns) {
        boolean state = false;
        int addedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && addStudent(pawnColor)) {
                i++;
                addedElements++;
            }
        }
        if (addedElements==pawns.totalElements()) state = true;
        return state;
    }

    public Optional<TowerColor> conquerIsland(List<Player> winners, List<Player> players){
        if(this.tower == null){
            if(winners.size() == 1){
                this.tower = winners.get(0).addTowerToBoard();
                return Optional.of(winners.get(0).getColor());
            }
        } else {
            if(winners.size() == 1){
                if(this.tower != (winners.get(0).getColor())){
                    for(Player player : players){
                        if(this.tower == (player.getColor())){
                            player.backTowerToPlayer();
                            this.tower = winners.get(0).addTowerToBoard();
                            return Optional.of(winners.get(0).getColor());
                        }
                    }
                }
            }
        }
        return Optional.empty();
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
}
