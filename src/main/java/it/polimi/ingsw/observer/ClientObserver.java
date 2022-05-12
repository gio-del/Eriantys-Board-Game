package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.Target;

/**
 * The client observer implements this interface.
 */
public interface ClientObserver {
    void updateConnection(String ip, int port);
    void updateNickname(String nickname);
    void updateGameModeNumPlayer(String mode, int numOfPlayer);
    void updateWizardAndColor(Wizard wizard, TowerColor towerColor);
    void updateAssistant(Assistant assistant);
    void updateCloud(int cloud);
    void updateStepsMN(int steps);
    void updateMoveStudent(PawnColor color, Target target, int island);
    void updateUseCharacter(int id);
    void updateColorAction(PawnColor chosen);
    void updateIslandAction(int island);
}
