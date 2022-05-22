package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.influencecalculator.CentaurStrategy;
import it.polimi.ingsw.model.influencecalculator.KnightStrategy;
import it.polimi.ingsw.model.influencecalculator.MushroomSellerStrategy;
import it.polimi.ingsw.model.player.Player;

/**
 * This action is used to set the strategy of the influence calculation to that specified by the character.
 */
public class SetInfluenceStrategyAction implements Action {
    private final CharacterCard chosenCard;
    private final Board board;
    private final Player player;

    public SetInfluenceStrategyAction(CharacterCard chosenCard, Board board, Player player) {
        this.chosenCard = chosenCard;
        this.board = board;
        this.player = player;
    }

    @Override
    public boolean apply() {
        switch (chosenCard.getName()) {
            case "Centaur" -> board.setStrategy(new CentaurStrategy());
            case "Knight" -> board.setStrategy(new KnightStrategy(player.getColor()));
            case "Mushroom Seller" ->
                    board.setStrategy(new MushroomSellerStrategy(chosenCard.getChosenColor())); // this need blockedColor
            default -> {
                return false;
            }
        }
        return true;
    }
}
