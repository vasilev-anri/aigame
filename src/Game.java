import java.util.Scanner;

/**
 * Manage player interaction and Game flow
 */
public class Game {

    private GameState state;
    private Scanner scanner;

    /**
     * Constructor for creating a new game with the given initial state
     * @param initialState - Initial game state (includes list of numbers and who starts the game)
     */
    public Game(GameState initialState) {
        this.state = initialState;
        this.scanner = new Scanner(System.in);
    }


    public void play() {
        System.out.println(state);
        System.out.println();

        while (!state.isGameOver()) {
            if (state.isPlayerTurn) playerMove();
            else computerMove();
            System.out.println(state);
            System.out.println();
        }

        System.out.println("GAME OVER");
        System.out.println("Winner: " + state.getWinner());
    }


    private void playerMove() {
        System.out.print("Player's turn! Enter index (0-" + (state.numbers.size() - 2) + "): ");
        int move = scanner.nextInt();
        state.move(move);
    }

    private void computerMove() {
        System.out.println("Computer's turn!");
        int move = 0;
        state.move(move);
        System.out.println("Computer chose index: " + move);
    }
}
