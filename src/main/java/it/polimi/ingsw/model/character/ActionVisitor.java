package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.server.TurnManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.action.*;
import it.polimi.ingsw.model.character.actiondata.*;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;

/**
 * This class is used to access characters data in order to build and execute their action.
 */
public record ActionVisitor(TurnManager turn, Game game, CharacterCard chosen) {

    public void visit(MoveData data) {
        Player player = game.getPlayerByName(turn.getRequestName());
        Action action = switch (data.getTo()) {
            case ENTRANCE, SELF -> null;
            case ISLAND -> new MoveAction(chosen, chosen.getChosenIsland(), new Pawns(chosen.getChosenColor()));
            case HALL ->
                    new MoveAction(chosen, player.getSchool().getHallAsPlace(), new Pawns(chosen.getChosenColor()));
        };
        validateAction(action);
    }

    public void visit(CalculateInfluenceData ignoredData) {
        Action action = new CalculateInfluenceAction(game.getPlayers(), game.getBoard(), chosen.getChosenIsland());
        validateAction(action);
    }

    public void visit(SetInfluenceStrategyData ignoredData) {
        Player player = game.getPlayerByName(turn.getRequestName());
        Action action = new SetInfluenceStrategyAction(chosen, game.getBoard(), player);
        validateAction(action);
    }

    public void visit(SetProfStrategyData ignoredData) {
        Player player = game.getPlayerByName(turn.getRequestName());
        Action action = new SetProfStrategyAction(chosen.getName(), game.getProfessorAssignor(), player);
        validateAction(action);
    }

    public void visit(ThiefData data) {
        Action action = new ThiefAction(chosen.getChosenColor(), game.getPlayers(), data.getRemovedStudents(), game.getSack());
        validateAction(action);
    }

    public void visit(SwapData data) {
        Player player = game.getPlayerByName(turn.getRequestName());
        Action action = switch (data.getFrom()) {
            case ENTRANCE, ISLAND -> null;
            case SELF ->
                    new SwapAction(chosen, player.getSchool().getEntranceAsPlace(), chosen.getChosenSwap(), data.getMaxSwaps());
            case HALL ->
                    new SwapAction(player.getSchool().getHallAsPlace(), player.getSchool().getEntranceAsPlace(), chosen.getChosenSwap(), data.getMaxSwaps());
        };
        validateAction(action);
    }

    public void visit(StepsIncrementData data) {
        Action action = new StepsIncrementAction(game, data.getIncrement());
        validateAction(action);
    }

    public void visit(BanData ignoredData) {
        Action action = new BanAction(chosen);
        validateAction(action);
    }

    /**
     * Validate the action and notifies the turn manager.
     *
     * @param action the action to be validated
     */
    private void validateAction(Action action) {
        if (action != null && action.apply()) {
            turn.onActionCompleted();
        } else turn.onActionFailed();
    }
}
