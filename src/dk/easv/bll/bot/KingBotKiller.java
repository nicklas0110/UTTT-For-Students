package dk.easv.bll.bot;

import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;

public class KingBotKiller implements IBot {
    private static final String BOTNAME="xXx_KingBotkiller_xXx";


    @Override
    public IMove doMove(IGameState state) {
        return null;
    }

    @Override
    public String getBotName() {
        return BOTNAME;
    }
}
