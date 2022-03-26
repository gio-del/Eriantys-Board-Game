package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.*;

import java.util.Random;
import java.util.stream.IntStream;

public class Sack {
    private final Pawns sackPawns;

    public Sack() {
        this.sackPawns = new Pawns();
    }

    public void fillStartGame() {
        for(PawnColor pawnColor: PawnColor.values()){
            sackPawns.addColor(pawnColor,Constants.NumOfStudentsOfEachColor);
        }
    }

    public PawnColor extract(){
        Random random = new Random();
        PawnColor extracted = sackPawns.getByIndex(random.nextInt(sackPawns.totalElements()));
        sackPawns.removeColor(extracted);
        return extracted;
    }

    public Pawns extractListOfPawns(int size) {
        Pawns extracted = new Pawns();
        IntStream.range(0, size).mapToObj(i -> extract()).forEach(extracted::addColor);
        return extracted;
    }

    public int getNumberOfPawns() {
        return sackPawns.totalElements();
    }
}
