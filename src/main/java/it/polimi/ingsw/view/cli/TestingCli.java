package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameLimit;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.place.School;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;

public class TestingCli {
    public static void main(String[] args) {
        School school;
        Pawns example, professorExample;
        View cli = new Cli();


        HallManager hallManager = new HallManager();
        Player player = new Player("Fausto", Wizard.KING, TowerColor.BLACK,new GameLimit(false), hallManager);
        hallManager.addPlayer(player);

        school = player.getSchool();
        example = new Pawns(3,0,3,0,1);
        school.addStudentInEntrance(example);
        school.addStudentInHall(example);
        school.addProfessor(PawnColor.BLUE);

        ShortSchool shortSchool = new ShortSchool(school);

        cli.showSchool(shortSchool);
    }
}
