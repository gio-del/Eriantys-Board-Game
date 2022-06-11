package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.Target;

import java.util.List;

/**
 * The client observer implements this interface.
 */
public interface ClientObserver {
    /**
     * Receive an update by a client about the chosen ip address and port number.
     * @param ip the chosen ip
     * @param port the chosen port number
     */
    void updateConnection(String ip, int port);

    /**
     * Receive an update by a client about the chosen nickname.
     * @param nickname the chosen nickname
     */
    void updateNickname(String nickname);

    /**
     * Receive an update by a client about the chosen game mode nad number of player
     * @param mode the chosen game mode
     * @param numOfPlayer the chosen number of player in the game
     */
    void updateGameModeNumPlayer(String mode, int numOfPlayer);

    /**
     * Receive an update by a client about the chosen wizard and tower color during the initial phase of the game.
     * @param wizard the chosen wizard
     * @param towerColor the chosen tower color
     */
    void updateWizardAndColor(Wizard wizard, TowerColor towerColor);

    /**
     * Receive an update by a client about the chosen assistant to play.
     * @param assistant the chosen assistant
     */
    void updateAssistant(Assistant assistant);

    /**
     * Receive an update by a client about the chosen cloud to pick students from.
     * @param cloud the chosen cloud
     */
    void updateCloud(int cloud);

    /**
     * Receive an update by a client about the num of steps that mother nature must perform.
     * @param steps the number of step to perform
     */
    void updateStepsMN(int steps);

    /**
     * Receive an update by a client regarding what student and where it must move.
     * @param color the color of the student to move
     * @param target the destination of this student (HALL or ISLAND)
     * @param island if the target is island this parameter is used to define what island to move the student to
     */
    void updateMoveStudent(PawnColor color, Target target, int island);

    /**
     * Receive an update by a client regarding what character he want to use.
     * @param id the id of the chosen character.
     */
    void updateUseCharacter(int id);

    /**
     * Receive an update by a client regarding the color provided to the action of a character.
     * @param chosen the chosen color
     */
    void updateColorAction(PawnColor chosen);

    /**
     * Receive an update by a client regarding the island provided to the action of a character.
     * @param island the chosen island
     */
    void updateIslandAction(int island);

    /**
     * Receive an update by a client regarding the swap list provided to the action of a character.
     * @param swapColor the chosen swap list
     */
    void updateSwapAction(List<PawnColor> swapColor);
}
