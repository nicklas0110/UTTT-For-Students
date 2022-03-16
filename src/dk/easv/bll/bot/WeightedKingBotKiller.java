package dk.easv.bll.bot;

import dk.easv.bll.field.IField;
import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;

import java.util.ArrayList;
import java.util.List;


public class WeightedKingBotKiller implements IBot {
    private static final String BOTNAME="xXx_KingBotkiller_xXx";


    @Override
    public IMove doMove(IGameState state) {

        //Weighted random
        List<IMove> moves = state.getField().getAvailableMoves();
        return randomWeightedMove(moves,calcWeights(state));

    }

    public IMove randomWeightedMove(List<IMove> moves,double[][] weights) {
        double completeWeight = 0.0;
        for (IMove move : moves)
            if (weights[move.getX()][move.getY()] != 0){
                completeWeight += weights[move.getX()][move.getY()];
            }
        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (IMove move : moves) {
            if (weights[move.getX()][move.getY()] != 0){
                countWeight += weights[move.getX()][move.getY()];
                if (countWeight >= r)
                    return move;
            }
        }
        throw new RuntimeException("Should never be shown.");
    }

    private double[][] calcWeights(IGameState state) {
        double[][] weights = new double[9][9];

        for (int i = 0;i < 9; i++){
            for (int j = 0;j < 9; j++){
                weights[i][j] = 100;
                weights[i][j] *= getP1Score(state,i,j);
                weights[i][j] *= getP2Score(state,i,j);
            }
        }

        for (int i = 0;i < 3; i++){
            for (int j = 0;j < 3; j++){
                if(!state.getField().getMacroboard()[i][j].equals(IField.AVAILABLE_FIELD)){

                    for (int x = 0;x < 3; x++){
                        for (int y = 0;y < 3; y++){
                            weights[i+x*3][j+y*3] *= 0.5;
                        }
                    }

                }
            }
        }

        for (IMove move: getWinningMoves(state)) {
            weights[move.getX()][move.getY()] *= 1000.0;
        }
        for (IMove move: getWinningMovesP2(state)) {
            weights[move.getX()][move.getY()] *= 1000.0;
        }

        for (int i = 0;i < 9; i++){
            for (int j = 0;j < 9; j++){
                if (weights[i][j] < 0)
                    weights[i][j] = 0;
            }
        }

        return weights;
    }

    private double getP1Score(IGameState state,int i,int j) {
        String player = "1";
        if (state.getMoveNumber() % 2 == 0)
            player = "0";

        String[][] board = state.getField().getBoard();
        double score = 1;
        double rowWeight = 2.0;
        double columnWeight = 2.0;
        double diagonalWeight = 2.0;

        // Row checking
        int startX = i - (i % 3);   // starting point
        int endX = startX + 2;
        for (int x = startX; x <= endX; x++) {
            if (board[x][j].equals(player))
                score *= rowWeight;
        }

        // Column checking
        int startY = j - (j % 3);   // starting point
        int endY = startY + 2;
        for (int y = startY; y <= endY; y++) {
            if (board[i][y].equals(player))
                score *= columnWeight;
        }

        if ((i % 3) % 2 + (j % 3) % 2 != 1){

            if (i % 3 == j % 3){
                // Diagonal checking left-top to right-bottom
                if (board[startX][startY].equals(player))
                    score *= diagonalWeight;
                if (board[startX+1][startY+1].equals(player))
                    score *= diagonalWeight;
                if (board[startX + 2][startY + 2].equals(player))
                    score *= diagonalWeight;
            }

            if ( i % 3 + j % 3 == 2){
                // Diagonal checking left-bottom to right-top
                if (board[startX][startY + 2].equals(player))
                    score *= diagonalWeight;
                if (board[startX+1][startY+1].equals(player))
                    score *= diagonalWeight;
                if (board[startX + 2][startY].equals(player))
                    score *= diagonalWeight;
            }
        }

        return score;
    }
    private double getP2Score(IGameState state,int i,int j) {
        String player = "0";
        if (state.getMoveNumber() % 2 == 0)
            player = "1";

        String[][] board = state.getField().getBoard();
        double score = 1;
        double rowWeight = 0.5;
        double columnWeight = 0.5;
        double diagonalWeight = 0.5;

        // Row checking
        int startX = i - (i % 3);   // starting point
        int endX = startX + 2;
        for (int x = startX; x <= endX; x++) {
            if (board[x][j].equals(player))
                score *= rowWeight;
        }

        // Column checking
        int startY = j - (j % 3);   // starting point
        int endY = startY + 2;
        for (int y = startY; y <= endY; y++) {
            if (board[i][y].equals(player))
                score *= columnWeight;
        }

        if ((i % 3) % 2 + (j % 3) % 2 != 1){

            if (i % 3 == j % 3){
                // Diagonal checking left-top to right-bottom
                if (board[startX][startY].equals(player))
                    score *= diagonalWeight;
                if (board[startX+1][startY+1].equals(player))
                    score *= diagonalWeight;
                if (board[startX + 2][startY + 2].equals(player))
                    score *= diagonalWeight;
            }

            if ( i % 3 + j % 3 == 2){
                // Diagonal checking left-bottom to right-top
                if (board[startX][startY + 2].equals(player))
                    score *= diagonalWeight;
                if (board[startX+1][startY+1].equals(player))
                    score *= diagonalWeight;
                if (board[startX + 2][startY].equals(player))
                    score *= diagonalWeight;
            }
        }

        return score;
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
