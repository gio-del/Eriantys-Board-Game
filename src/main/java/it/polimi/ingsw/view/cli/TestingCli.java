package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.place.School;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

import static it.polimi.ingsw.model.player.TowerColor.*;
import static java.lang.System.exit;

public class TestingCli {
    public static void main(String[] args) {
        Cli cli = new Cli();
        Game game = new Game(2, false);
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.SORCERER,TowerColor.GRAY);
        HallManager hallManager = new HallManager();
        hallManager.addPlayer(game.getPlayers().get(0));

        game.getBoard().getIslands().get(0).add(new Pawns(3,5,3,2,1));
        game.getBoard().getIslands().get(1).add(new Pawns(1,5,10,4,3));
        game.getBoard().getIslands().get(2).add(new Pawns(1,2,7,4,3));
        game.getBoard().getIslands().get(3).add(new Pawns(1,2,7,4,3));
        game.getBoard().getIslands().get(4).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(5).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(6).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(7).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(8).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(9).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(10).add(new Pawns(1,2,3,4,3));
        game.getBoard().getIslands().get(11).add(new Pawns(1,2,3,4,3));

        game.getBoard().getIslands().get(0).addTower(BLACK);
        game.getBoard().getIslands().get(1).addTower(BLACK);
        game.getBoard().getIslands().get(3).addTower(GRAY);

        game.getBoard().adjacencyUpdate();

        School school = game.getPlayers().get(0).getSchool();
        Pawns example = new Pawns(3,0,3,0,1);
        school.addStudentInEntrance(example);
        school.addStudentInHall(example);
        school.addProfessor(PawnColor.BLUE);

        ShortSchool shortSchool = new ShortSchool(school);

        cli.updateScreen(game.getBoard(), shortSchool);

        exit(0);

    }

}

