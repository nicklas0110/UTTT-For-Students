package dk.easv.bll.bot;


import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KingBotKiller implements IBot {
    private static final String BOTNAME="KingBotkiller";
    Random rand = new Random();


    @Override
    public IMove doMove(IGameState state) {

        //Smart random
        List<IMove> winMoves = getWinningMoves(state);
        List<IMove> winMovesP2 = getWinningMovesP2(state);
        if (!winMoves.isEmpty())
            return winMoves.get(0);
        if (!winMovesP2.isEmpty())
            return winMovesP2.get(0);
        List<IMove> moves = state.getField().getAvailableMoves();
        if (moves.size() > 0) {
            return moves.get(rand.nextInt(moves.size())); /* get random move from available moves */
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
