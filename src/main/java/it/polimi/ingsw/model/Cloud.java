package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.*;
import static it.polimi.ingsw.model.pawns.PawnColor.*;

public class Cloud {
    int newGreen,newRed,newYellow,newPink,newBlue;
    Pawns student = new Pawns();

    public Pawns getPawnsAndRemove() {
        Pawns extracted = new Pawns();
        extracted.state.put(GREEN,student.state.get(GREEN));
        student.state.put(GREEN,0);
        extracted.state.put(RED,student.state.get(RED));
        student.state.put(RED,0);
        extracted.state.put(YELLOW,student.state.get(YELLOW));
        student.state.put(YELLOW,0);
        extracted.state.put(PINK,student.state.get(PINK));
        student.state.put(PINK,0);
        extracted.state.put(BLUE,student.state.get(BLUE));
        student.state.put(BLUE,0);

        return extracted;
    }

    public void fill(Pawns arriving) {
        newGreen = arriving.state.get(GREEN);
        student.state.put(GREEN,student.state.get(GREEN)+newGreen);
        newRed = arriving.state.get(RED);
        student.state.put(RED,student.state.get(RED)+newRed);
        newYellow = arriving.state.get(YELLOW);
        student.state.put(YELLOW,student.state.get(YELLOW)+newYellow);
        newPink = arriving.state.get(PINK);
        student.state.put(PINK,student.state.get(PINK)+newPink);
        newBlue = arriving.state.get(BLUE);
        student.state.put(BLUE,student.state.get(BLUE)+newBlue);
    }

}
