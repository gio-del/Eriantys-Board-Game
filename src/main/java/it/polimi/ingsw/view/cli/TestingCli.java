package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.School;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

import static it.polimi.ingsw.model.player.TowerColor.*;
import static java.lang.System.exit;

public class TestingCli {
    public static void main(String[] args) {
        Cli cli = new Cli();
        Game game = new Game();
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.SORCERER,TowerColor.GRAY);
        game.init();

        game.getBoard().getIslands().get(0).add(new Pawns(3,5,0,2,0));
        game.getBoard().getIslands().get(1).add(new Pawns(1,0,0,4,3));
        game.getBoard().getIslands().get(2).add(new Pawns(1,2,0,0,0));
        game.getBoard().getIslands().get(3).add(new Pawns(0,0,1,4,1));
        game.getBoard().getIslands().get(4).add(new Pawns(0,2,3,4,2));
        game.getBoard().getIslands().get(5).add(new Pawns(1,0,0,0,3));
        game.getBoard().getIslands().get(6).add(new Pawns(0,2,3,4,0));
        game.getBoard().getIslands().get(7).add(new Pawns(1,2,1,0,1));
        game.getBoard().getIslands().get(8).add(new Pawns(1,0,3,0,0));
        game.getBoard().getIslands().get(9).add(new Pawns(0,2,0,0,2));
        game.getBoard().getIslands().get(10).add(new Pawns(0,0,0,0,1));
        game.getBoard().getIslands().get(11).add(new Pawns(0,0,0,0,0));

        game.getBoard().getIslands().get(0).addTower(BLACK);
        game.getBoard().getIslands().get(1).addTower(BLACK);
        game.getBoard().getIslands().get(2).addTower(BLACK);
        game.getBoard().getIslands().get(3).addTower(BLACK);
        game.getBoard().getIslands().get(4).addTower(BLACK);
        game.getBoard().getIslands().get(5).addTower(BLACK);

        game.getBoard().adjacencyUpdate();
        game.getBoard().moveMotherNature(6, game.getPlayers());

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

