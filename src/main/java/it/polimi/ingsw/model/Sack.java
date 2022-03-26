package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.*;
import static it.polimi.ingsw.model.pawns.PawnColor.*;

public class Sack {
    Pawns sackPawns = new Pawns();
    int numberOfPawns;

    public void fillPreparing() {
        sackPawns.state.put(GREEN,2);
        sackPawns.state.put(RED,2);
        sackPawns.state.put(YELLOW,2);
        sackPawns.state.put(PINK,2);
        sackPawns.state.put(BLUE,2);
        numberOfPawns = 10;
    }

    public void fillStartGame() {
        sackPawns.state.put(GREEN,24);
        sackPawns.state.put(RED,24);
        sackPawns.state.put(YELLOW,24);
        sackPawns.state.put(PINK,24);
        sackPawns.state.put(BLUE,24);
        numberOfPawns = 120;
    }

    public PawnColor extract() {
      numberOfPawns--;               //fix with media-ponderata
       return GREEN;
    }

    public Pawns extractListOfPawns(int size) {
        Pawns extracted = new Pawns();
        for (int i=0;i < size;i ++) {
            extracted.state.put(extract(),1);
        }
        return extracted;
    }

    public int numberOfPawns() {
        return numberOfPawns;
    }
}
