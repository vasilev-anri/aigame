import java.util.List;


/**
 * Implements alpha-beta pruning algorithm
 *
 */
public class AlphaBeta {

    private static int nodesVisited = 0;

    /**
     * finds the best move for the computer from the current state of game
     *
     * @param state
     * @param depth - how many moves ahead to look (N-ply look ahead)
     * @return index of the best move to make
     */
    public int findBestMove(GameState state, int depth) {
        resetNodesVisited();
        List<GameState> children = state.getPossibleMoves();            // all possible mvoes from current state
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


    /**
     * core algorithm (recursive)
     * evaluates game state by exploring future moeves up to the depth limit.
     *
     * @param state - current game state being evaluated
     * @param depth - remaining depth to search (decreases each recusrive call)
     * @param alpha - best value for maximizing player (computer) found so far
     * @param beta - best value for minimizing player (huma) found so far
     * @param maximizingPlayer - true if current player is computer, else: false
     * @return evaluated score for this state
     */
    private int alphaBeta(GameState state, int depth, int alpha, int beta, boolean maximizingPlayer) {

        nodesVisited += 1;

        // base case - depth limit reached or game is over
        if (depth == 0 || state.isGameOver()) return state.evaluate();

        // all possible next states from current position
        List<GameState> children = state.getPossibleMoves();

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (GameState child : children) {
                value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, value);
                if (beta <= alpha) break; // beta cut-off
            }
            return value;
        }
        else {
            int value = Integer.MAX_VALUE;
            for (GameState child : children) {
                value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta, value);
                if (beta <= alpha) break; // alpha cut-off
            }
            return value;
        }
    }

    public static int getNodesVisited() {
        return nodesVisited;
    }

    public static void resetNodesVisited() {
        nodesVisited = 0;
    }
}
