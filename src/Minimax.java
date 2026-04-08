import java.util.List;


/**
 * Implements Minimax algorithm
 */
public class Minimax {

    private static int nodesVisited;


    /**
     * finds the best move for the computer from the current state of game
     *
     * @param state - current game state
     * @param depth - how many moves ahead to look (N-ply look ahead)
     * @return - index of the best move to make
     */
    public int findBestMove(GameState state, int depth) {
        resetNodesVisited();
        List<GameState> children = state.getPossibleMoves();
        if (children.isEmpty()) return 0;

        int bestMove = 0;
        int bestValue = Integer.MIN_VALUE;

        for (int i = 0; i < children.size(); i++) {
            int value = minimax(children.get(i), depth - 1, false);
            if (value > bestValue) {
                bestValue = value;
                bestMove = i;
            }
        }

        return bestMove;
    }


    /**
     * Minimax algorithm (recursive, depth-limited)
     * @param node
     * @param depth
     * @param maximizingPlayer
     * @return
     */
    private int minimax(GameState node, int depth, boolean maximizingPlayer) {
        nodesVisited += 1;
        if (depth == 0 || node.isGameOver()) return node.evaluate();

        List<GameState> children = node.getPossibleMoves();

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (GameState child : children) {
                value = Math.max(value, minimax(child, depth - 1, false));
            }
            return value;
        }
        else {
            int value = Integer.MAX_VALUE;
            for (GameState child : children) {
                value = Math.min(value, minimax(child, depth - 1, true));
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
