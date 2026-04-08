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
    private AlphaBeta alphaBeta;
    private Minimax minimax;
    private JTextArea displayArea;
    private JTextField inputField;
    private JButton moveButton;
    private JButton newGameButton;
    private Random random;
    private boolean useAlphaBeta = true;
    int depthLimit = 4;
    private JLabel label;
    private long computerThinkTime = 0;
    private int computerMove = -1;

    public GameUI() {

        alphaBeta = new AlphaBeta();
        minimax = new Minimax();


        label = new JLabel("Ready");
        JPanel panel = new JPanel();
        panel.add(label);
        add(panel, BorderLayout.NORTH);


        setTitle("Number Pair Game");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


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

        computerThinkTime = 0;
        computerMove = -1;

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

            String[] algs = {"Alpha-Beta", "Minimax"};
            int chooseAlgorithm = JOptionPane.showOptionDialog(this,
                    "Choose Algorithm",
                    "Choose Algorithm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    algs,
                    algs[0]
            );

            useAlphaBeta = (chooseAlgorithm == JOptionPane.YES_OPTION);


            String[] depths = {"3", "4", "5", "6"};
            int depthChoice = JOptionPane.showOptionDialog(this,
                    """
                            Choose search depth limit
                            Higher depth - slower performance
                            Recommended depth: 4""",
                    "Choose depth",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    depths,
                    depths[1]);

            switch (depthChoice) {
                case 0:
                    depthLimit = 3;
                    break;
                case 2:
                    depthLimit = 5;
                    break;
                case 3:
                    depthLimit = 6;
                    break;
                default:
                    depthLimit = 4;
            }


            String alg = useAlphaBeta ? "Alpha-Beta" : "Minimax";
            JOptionPane.showMessageDialog(this,
                    "Computer will use " + alg + " algorithm\nDepth: " + depthLimit + "\nString length: " + length,
                    "Algorithm Selected",
                    JOptionPane.INFORMATION_MESSAGE);

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
                long startTime = System.currentTimeMillis();

                if (useAlphaBeta) computerMove = alphaBeta.findBestMove(state, depthLimit);
                else computerMove = minimax.findBestMove(state, depthLimit);

                long endTime = System.currentTimeMillis();

                computerThinkTime = endTime - startTime;

                state.move(computerMove);
                return null;
            }

            @Override
            protected void done() {
                updateDisplay();

                SwingUtilities.invokeLater(() -> {
                    label.setText("Computer thought for: " +
                            computerThinkTime + "Ms; index chosen: " + computerMove + "\n");
                });

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