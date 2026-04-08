import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Experiment {

    public static void main(String[] args) {

        runExperiment("Minimax", 20);
        runExperiment("AlphaBeta", 20);
    }

    private static void runExperiment(String algorithm, int games) {

        int playerWins = 0;
        int computerWins = 0;
        int draws = 0;
        long totalTime = 0;
        int totalNodes = 0;

        Random random = new Random();

        for (int g = 1; g <= games; g++) {

            int length = 15 + random.nextInt(11);
            List<Integer> numbers = new LinkedList<>();
            for (int i = 0; i < length; i++) {
                numbers.add(random.nextInt(9) + 1);
            }

            boolean playerStarts = random.nextBoolean();
            GameState state = new GameState(numbers, playerStarts);

            long startTime = System.currentTimeMillis();
            int gameNodes = 0;

            while (!state.isGameOver()) {

                int move;

                if (algorithm.equals("AlphaBeta")) {
                    AlphaBeta.resetNodesVisited();
                    move = new AlphaBeta().findBestMove(state, 4);
                    gameNodes += AlphaBeta.getNodesVisited();
                }
                else {
                    Minimax.resetNodesVisited();
                    move = new Minimax().findBestMove(state, 4);
                    gameNodes += Minimax.getNodesVisited();
                }

                state.move(move);

            }

            long endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
            totalNodes += gameNodes;

            String winner = state.getWinner();

            if (winner.equals("Player")) playerWins++;
            else if (winner.equals("Computer")) computerWins++;
            else draws++;

            System.out.println("Game " + g + " winner: " + winner);
        }

        System.out.println("\n=== " + algorithm + " RESULTS ===");
        System.out.println("Player Wins: " + playerWins);
        System.out.println("Computer Wins: " + computerWins);
        System.out.println("Draws: " + draws);
        System.out.println("Average Time: " + (totalTime / games) + " ms");
        System.out.println("Average Nodes: " + (totalNodes / games));
        System.out.println();
    }
}