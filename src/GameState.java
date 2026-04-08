import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A single state in the game.
 * Encapsulates all the information about the game at a moment.
 */

public class GameState {

    private List<Integer> numbers;
    private int totalPoints;
    private int bank;
    private boolean isPlayerTurn;
    private boolean playerStarted;           // used for winner determination at the end of the game

    /**
     * Constructor for creating new initial game state
     * all counters at zero, remembers who started the game
     *
     * @param numbers      - starting sequence of numbers
     * @param playerStarts - true if players starts, false if computer starts
     */
    public GameState(List<Integer> numbers, boolean playerStarts) {
        this.numbers = new LinkedList<>(numbers);
        this.totalPoints = 0;
        this.bank = 0;
        this.isPlayerTurn = playerStarts;
        this.playerStarted = playerStarts;
    }

    /**
     * Copy constructor for creating a copy of another game state
     * when exploring possible moves, new stated must be created without modifying original ones
     * each branch of the game tree has its own copy
     *
     * @param other - the game state to copy
     */
    public GameState(GameState other) {
        this.numbers = new LinkedList<>(other.numbers);
        this.totalPoints = other.totalPoints;
        this.bank = other.bank;
        this.isPlayerTurn = other.isPlayerTurn;
        this.playerStarted = other.playerStarted;
    }

    /**
     * make a move by combining the pair at specified index
     *
     * @param pairFirstIndex If sum > 7: replace with 1, add 1 to total
     *                       If sum < 7: replace with 3, subtract 1 from total
     *                       If sum = 7: replace with 2, add 1 to bank
     *
     */
    public void move(int pairFirstIndex) {
        int sum = numbers.get(pairFirstIndex) + numbers.get(pairFirstIndex + 1);

        switch (Integer.compare(sum, 7)) {
            case 1:
                replacePair(pairFirstIndex, 1);
                totalPoints += 1;
                break;
            case -1:
                replacePair(pairFirstIndex, 3);
                totalPoints -= 1;
                break;
            case 0:
                bank += 1;
                replacePair(pairFirstIndex, 2);
                break;
        }

        isPlayerTurn = !isPlayerTurn;   // switch to another player
    }


    /**
     * Helper method for replacing a pair with a single number
     *
     * @param idx - index of the first number in pair
     * @param val - value to place at index (idx)
     */
    private void replacePair(int idx, int val) {
        numbers.remove(idx + 1);    // remove second number in pair
        numbers.set(idx, val);            // change first number with val (new value)
    }

    public boolean isGameOver() {
        return numbers.size() == 1;
    }


    /**
     * determine the winner
     * <p>
     * if total & bank are even (EE): who started wins
     * if total & bank are odd (OO): another player wins
     * else: draw
     *
     * @return
     */
    public String getWinner() {
        if (!isGameOver()) return "Not finished yet";

        boolean totalEven = (totalPoints % 2 == 0);
        boolean bankEven = (bank % 2 == 0);

        return switch ((totalEven ? "E" : "O") + (bankEven ? "E" : "O")) {
            case "EE" -> playerStarted ? "Player" : "Computer";
            case "OO" -> playerStarted ? "Computer" : "Player";
            default -> "Draw";
        };
    }

    /**
     * Generates all possible next states from the current position
     *
     * @return - list of all possible game states (objects) after each valid move
     */
    public List<GameState> getPossibleMoves() {
        List<GameState> moves = new ArrayList<>();

        // for each adj pair in the current list of nums
        for (int i = 0; i < numbers.size() - 1; i++) {
            GameState newState = new GameState(this);   // copy current state
            newState.move(i);
            moves.add(newState);
        }

        return moves;
    }

    /**
     * Evaluate current game state
     * Heuristic evaluation function
     * <p>
     * higher score - better for computer, lower score - better for player
     * helps AI decide which moves lead to better positions
     *
     * @return - numerical score
     */
    public int evaluate() {
        if (isGameOver()) {
            String winner = getWinner();
            if (winner.equals("Computer")) return Integer.MAX_VALUE;
            if (winner.equals("Player")) return Integer.MIN_VALUE;
            return 0;
        }

        int score = 0;

        score += totalPoints * 10;
        score += bank * 5;
        score += (10 - numbers.size()) * 2;

        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) + numbers.get(i + 1) == 7) score += 3;
        }

        return score;
    }

    public String displayIndices() {
        StringBuilder sb = new StringBuilder();
        sb.append("Indices: ");

        for (int i = 0; i < numbers.size(); i++) {
            sb.append(String.format("%3d ", i));
        }
        sb.append("\n");

        sb.append("Numbers: ");
        for (int n : numbers) {
            sb.append(String.format("%3d ", n));
        }

        sb.append(String.format(" | Total: %d, Bank: %d | %s's turn (Started: %s)", totalPoints, bank, isPlayerTurn ? "Player" : "Computer", playerStarted ? "Player" : "Computer"));
        return sb.toString();
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getBank() {
        return bank;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public boolean isPlayerStarted() {
        return playerStarted;
    }

    /**
     * Human-readable game state representation
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("Numbers: %s | Total: %d, Bank: %d | %s's turn (Started: %s)",
                numbers, totalPoints, bank,
                isPlayerTurn ? "Player" : "Computer",
                playerStarted ? "Player" : "Computer");
    }
}
