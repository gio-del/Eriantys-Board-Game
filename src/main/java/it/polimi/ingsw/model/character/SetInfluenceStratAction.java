package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.influencecalculator.CentaurStrategy;
import it.polimi.ingsw.model.influencecalculator.KnightStrategy;
import it.polimi.ingsw.model.influencecalculator.MushroomSellerStrategy;
import it.polimi.ingsw.model.player.Player;

public class SetInfluenceStratAction implements Action{
    private final String influenceChoice;
    private final Board board;
    private final Player player;

    public SetInfluenceStratAction(String influenceChoice, Board board, Player player) {
        this.influenceChoice = influenceChoice;
        this.board = board;
        this.player = player;
    }

    @Override
    public boolean apply() {
        // TODO: maybe influenceChoice is just character name, also remove hardcoded strings literal
        // TODO: where does blockedColor in MushroomSeller come from?
        switch (influenceChoice){
            case "Centaur" -> board.setStrategy(new CentaurStrategy());
            case "Knight" -> board.setStrategy(new KnightStrategy(player.getColor()));
            case "MushroomSeller" -> board.setStrategy(new MushroomSellerStrategy()); // this need blockedColor
            default -> {
                return false;
            }
        }
        return false;
    }
}
