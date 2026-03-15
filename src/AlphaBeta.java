import java.util.List;

public class AlphaBeta {

    public int findBestMove(GameState state, int depth) {
        List<GameState> children = state.getPossibleMoves();
        int bestMove = 0;
        int bestValue = Integer.MIN_VALUE;

        for (int i = 0; i < children.size(); i++) {
            int value = alphaBeta(children.get(i), depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (value > bestValue) {
                bestValue = value;
                bestMove = i;
            }
        }

        return bestMove;
    }

    private int alphaBeta(GameState state, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || state.isGameOver()) return state.evaluate();

        List<GameState> children = state.getPossibleMoves();

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (GameState child : children) {
                value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, value);
                if (beta <= alpha) break;
            }
            return value;
        }
        else {
            int value = Integer.MAX_VALUE;
            for (GameState child : children) {
                value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta, value);
                if (beta <= alpha) break;
            }
            return value;
        }
    }

}
