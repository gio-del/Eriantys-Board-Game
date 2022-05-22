package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.character.CharacterCard;

/**
 * The ban action is used to ban an island.
 */
public class BanAction implements Action {
    private final CharacterCard characterCard;

    public BanAction(CharacterCard characterCard) {
        this.characterCard = characterCard;
    }

    @Override
    public boolean apply() {
        if (characterCard.getNumberOfBanTiles() > 0) {
            characterCard.getChosenIsland().upgradeBanTiles(1);
            characterCard.setNumberOfBanTiles(characterCard.getNumberOfBanTiles() - 1);
            return true;
        }
        return false;
    }
}
