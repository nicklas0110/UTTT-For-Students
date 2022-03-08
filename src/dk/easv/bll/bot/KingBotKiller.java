package dk.easv.bll.bot;

import dk.easv.bll.field.IField;
import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;
import dk.easv.bll.move.Move;

import java.util.ArrayList;
import java.util.List;

public class KingBotKiller implements IBot {
    private static final String BOTNAME="xXx_KingBotkiller_xXx";

    // Moves {row, col} in order of preferences. {0, 0} at top-left corner
    protected int[][] preferredMovesForKing = {
            {1, 1}, //Center
            {0, 0}, {2, 2}, {0, 2}, {2, 0},  //Corners ordered across
            {0, 1}, {2, 1}, {1, 0}, {1, 2}}; //Outer Middles ordered across


    @Override
    public IMove doMove(IGameState state) {
        //Find macroboard to play in
        for (int[] move : preferredMovesForKing) {
            if (state.getField().getMacroboard()[move[0]][move[1]].equals(IField.AVAILABLE_FIELD))
            {
                List<IMove> winMoves = getWinningMoves(state);
                List<IMove> winMovesP2 = getWinningMovesP2(state);
                if (!winMoves.isEmpty())
                    return winMoves.get(0);
                if (!winMoves.isEmpty())
                    return winMovesP2.get(0);
                //find move to play
                for (int[] selectedMove : preferredMovesForKing) {
                    int x = move[0] * 3 + selectedMove[0];
                    int y = move[1] * 3 + selectedMove[1];
                    if (state.getField().getBoard()[x][y].equals(IField.EMPTY_FIELD)) {
                        return new Move(x, y);
                    }
                }
            }
        }

        //NOTE: Something failed, just take the first available move I guess!
        return state.getField().getAvailableMoves().get(0);
    }

    private List<IMove> getWinningMovesP2(IGameState state) {
        String player = "0";
        if (state.getMoveNumber() % 2 == 0)
            player = "1";

        List<IMove> avail = state.getField().getAvailableMoves();
        String[][] board = state.getField().getBoard();


        List<IMove> winningMoves = new ArrayList<>();
        for (IMove move : avail) {
            boolean isRowWin = true;
            // Row checking
            int startX = move.getX() - (move.getX() % 3);   // starting point
            int endX = startX + 2;
            for (int x = startX; x <= endX; x++) {
                if (x != move.getX())
                    if (!board[x][move.getY()].equals(player))
                        isRowWin = false;
            }
            if (isRowWin) {
                winningMoves.add(move);
                //break;
            }


            // Column checking
            boolean isColumnWin = true;
            int startY = move.getY() - (move.getY() % 3);   // starting point
            int endY = startY + 2;
            for (int y = startY; y <= endY; y++) {
                if (y != move.getY())
                    if (!board[move.getX()][y].equals(player))
                        isColumnWin = false;
            }

            if (isColumnWin) {
                winningMoves.add(move);
                //break;
            }

            boolean isDiagWin = true;

            // Diagonal checking left-top to right-bottom
            if (!(move.getX()==startX && move.getY()==startY))
                if (!board[startX][startY].equals(player))
                    isDiagWin = false;
            if (!(move.getX()==startX+1 && move.getY()==startY+1))
                if (!board[startX+1][startY+1].equals(player))
                    isDiagWin = false;
            if (!(move.getX()==startX+2 && move.getY()==startY+2))
                if (!board[startX + 2][startY + 2].equals(player))
                    isDiagWin = false;

            if (isDiagWin) {
                winningMoves.add(move);
                //break;
            }

            boolean isOppositeDiagWin = true;
            // Diagonal checking left-bottom to right-top
            if (!(move.getX()==startX && move.getY()==startY+2))
                if (!board[startX][startY + 2].equals(player))
                    isOppositeDiagWin = false;
            if (!(move.getX()==startX+1 && move.getY()==startY+1))
                if (!board[startX + 1][startY + 1].equals(player))
                    isOppositeDiagWin = false;
            if (!(move.getX()==startX+2 && move.getY()==startY))
                if (!board[startX + 2][startY].equals(player))
                    isOppositeDiagWin = false;
            if (isOppositeDiagWin) {
                winningMoves.add(move);
                //break;
            }

        }
        return winningMoves;
    }

    private List<IMove> getWinningMoves(IGameState state) {
        String player = "1";
        if (state.getMoveNumber() % 2 == 0)
            player = "0";

        List<IMove> avail = state.getField().getAvailableMoves();
        String[][] board = state.getField().getBoard();


        List<IMove> winningMoves = new ArrayList<>();
        for (IMove move : avail) {
            boolean isRowWin = true;
            // Row checking
            int startX = move.getX() - (move.getX() % 3);   // starting point
            int endX = startX + 2;
            for (int x = startX; x <= endX; x++) {
                if (x != move.getX())
                    if (!board[x][move.getY()].equals(player))
                        isRowWin = false;
            }
            if (isRowWin) {
                winningMoves.add(move);
                //break;
            }


            // Column checking
            boolean isColumnWin = true;
            int startY = move.getY() - (move.getY() % 3);   // starting point
            int endY = startY + 2;
            for (int y = startY; y <= endY; y++) {
                if (y != move.getY())
                    if (!board[move.getX()][y].equals(player))
                        isColumnWin = false;
            }

            if (isColumnWin) {
                winningMoves.add(move);
                //break;
            }

            boolean isDiagWin = true;

            // Diagonal checking left-top to right-bottom
            if (!(move.getX()==startX && move.getY()==startY))
                if (!board[startX][startY].equals(player))
                    isDiagWin = false;
            if (!(move.getX()==startX+1 && move.getY()==startY+1))
                if (!board[startX+1][startY+1].equals(player))
                    isDiagWin = false;
            if (!(move.getX()==startX+2 && move.getY()==startY+2))
                if (!board[startX + 2][startY + 2].equals(player))
                    isDiagWin = false;

            if (isDiagWin) {
                winningMoves.add(move);
                //break;
            }

            boolean isOppositeDiagWin = true;
            // Diagonal checking left-bottom to right-top
            if (!(move.getX()==startX && move.getY()==startY+2))
                if (!board[startX][startY + 2].equals(player))
                    isOppositeDiagWin = false;
            if (!(move.getX()==startX+1 && move.getY()==startY+1))
                if (!board[startX + 1][startY + 1].equals(player))
                    isOppositeDiagWin = false;
            if (!(move.getX()==startX+2 && move.getY()==startY))
                if (!board[startX + 2][startY].equals(player))
                    isOppositeDiagWin = false;
            if (isOppositeDiagWin) {
                winningMoves.add(move);
                //break;
            }

        }
        return winningMoves;
    }


    @Override
    public String getBotName() {
        return BOTNAME;
    }
}
