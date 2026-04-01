import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class GameUI extends JFrame {
    private GameState state;
    private AlphaBeta ai;
    private JTextArea displayArea;
    private JTextField inputField;
    private JButton moveButton;
    private JButton newGameButton;
    private Random random;

    public GameUI() {

        setTitle("Number Pair Game");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        ai = new AlphaBeta();
        random = new Random();


        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);


        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter index:"));
        inputField = new JTextField(5);
        inputPanel.add(inputField);

        moveButton = new JButton("Make Move");
        inputPanel.add(moveButton);

        newGameButton = new JButton("New Game");
        inputPanel.add(newGameButton);


        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);


        startNewGame();


        moveButton.addActionListener(this::makeMove);
        newGameButton.addActionListener(e -> startNewGame());
    }

    private void startNewGame() {
        String lengthStr = JOptionPane.showInputDialog(this,
                "Enter string length (15-25):",
                "New Game",
                JOptionPane.QUESTION_MESSAGE);

        if (lengthStr == null) return; // canceled

        try {
            int length = Integer.parseInt(lengthStr);
            if (length < 15 || length > 25) {
                JOptionPane.showMessageDialog(this,
                        "Length must be between 15 and 25!");
                return;
            }

            List<Integer> numbers = new LinkedList<>();
            for (int i = 0; i < length; i++) {
                numbers.add(random.nextInt(9) + 1);
            }

            int startChoice = JOptionPane.showOptionDialog(this,
                    "Who starts?",
                    "Choose First Player",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Player", "Computer"},
                    "Player");

            boolean playerStarts = (startChoice == JOptionPane.YES_OPTION);

            state = new GameState(numbers, playerStarts);
            updateDisplay();

            if (!state.isPlayerTurn && !state.isGameOver()) {
                SwingUtilities.invokeLater(this::computerMove);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number!");
        }
    }

    private void makeMove(ActionEvent e) {
        if (state == null || state.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game is over! Start a new game.");
            return;
        }

        if (!state.isPlayerTurn) {
            JOptionPane.showMessageDialog(this, "It's computer's turn!");
            return;
        }

        try {
            int index = Integer.parseInt(inputField.getText());
            if (index < 0 || index > state.numbers.size() - 2) {
                JOptionPane.showMessageDialog(this,
                        "Index must be between 0 and " + (state.numbers.size() - 2));
                return;
            }

            state.move(index);
            inputField.setText("");
            updateDisplay();

            if (state.isGameOver()) {
                showGameOver();
                return;
            }

            computerMove();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }

    private void computerMove() {
        if (state.isGameOver()) return;

        moveButton.setEnabled(false);
        inputField.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                int move = ai.findBestMove(state, 4);
                state.move(move);
                return null;
            }

            @Override
            protected void done() {
                updateDisplay();
                moveButton.setEnabled(true);
                inputField.setEnabled(true);

                if (state.isGameOver()) {
                    showGameOver();
                }
            }
        };
        worker.execute();
    }

    private void updateDisplay() {
        displayArea.setText(state.displayIndices());
        displayArea.setCaretPosition(0);
    }

    private void showGameOver() {
        String winner = state.getWinner();
        JOptionPane.showMessageDialog(this,
                "Game Over!\nWinner: " + winner,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameUI().setVisible(true);
        });
    }
}