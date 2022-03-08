package dk.easv.bll.bot;

import dk.easv.bll.game.IGameState;

/**
 *
 * @author mjl
 */
public interface IBot {

    /**
     * Makes a turn. Implement this method to make your dk.easv.bll.bot do something.
     *
     * @param state the current dk.easv.bll.game state
     * @return The column where the turn was made.
     */
    Object doMove(IGameState state);

    String getBotName();

}
