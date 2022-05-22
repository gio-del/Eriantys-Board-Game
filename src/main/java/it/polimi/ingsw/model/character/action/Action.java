package it.polimi.ingsw.model.character.action;

/**
 * This interface is implemented by each Characters' action. Each type of action is built at runtime with the provided input, if required.
 */
public interface Action {
    /**
     * Apply the action associated to the chosen character.
     *
     * @return true if the action is executed correctly, otherwise false
     */
    boolean apply();
}
