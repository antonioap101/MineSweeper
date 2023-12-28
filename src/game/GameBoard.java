import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class GameBoard {
    private MinesweeperGUI gui;
    private JButton[][] cells;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int rows;
    private int columns;
    private int numMines;
    private int flagsRemaining;
    private JPanel boardPanel;

    public GameBoard(MinesweeperGUI gui) {
        this.gui = gui;
        this.boardPanel = new JPanel(new GridLayout(10, 10));
    }

    public void initializeBoard(int rows, int columns, int numMines) {
        this.rows = rows;
        this.columns = columns;
        this.numMines = numMines;
        this.flagsRemaining = numMines;
        this.boardPanel = new JPanel(new GridLayout(rows, columns));

        cells = new JButton[rows][columns];
        mines = new boolean[rows][columns];
        revealed = new boolean[rows][columns];
        flagged = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new JButton("");
                cells[i][j].addActionListener(new CellActionListener(i, j));
                cells[i][j].addMouseListener(new FlagMouseListener(i, j));
                boardPanel.add(cells[i][j]); // Agregar el botón al panel del tablero
            }
        }

        placeMinesRandomly();
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    private void placeMinesRandomly() {
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = (int) (Math.random() * rows);
            int column = (int) (Math.random() * columns);

            if (!mines[row][column]) {
                mines[row][column] = true;
                minesPlaced++;
            }
        }
    }

    private void revealCell(int row, int column) {
        if (revealed[row][column] || flagged[row][column]) {
            return;
        }

        revealed[row][column] = true;
        cells[row][column].setEnabled(false);

        if (mines[row][column]) {
            // Has perdido, muestra todas las minas y finaliza el juego
            revealMines();
            JOptionPane.showMessageDialog(gui, "¡Has perdido!");
        } else {
            int count = countAdjacentMines(row, column);
            if (count == 0) {
                // Si la casilla está vacía, revela las casillas adyacentes
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int newRow = row + i;
                        int newColumn = column + j;
                        if (isValidCell(newRow, newColumn)) {
                            revealCell(newRow, newColumn);
                        }
                    }
                }
            } else {
                // Casilla no es una mina, muestra el número
                cells[row][column].setText(String.valueOf(count));
            }

            if (hasWon()) {
                JOptionPane.showMessageDialog(gui, "¡Has ganado!");
            }
        }
    }

    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newColumn = column + j;
                if (isValidCell(newRow, newColumn) && mines[newRow][newColumn]) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidCell(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public int getFlagsRemaining(){
        return this.flagsRemaining;
    }

    private class CellActionListener implements ActionListener {
        private int row;
        private int column;

        public CellActionListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!revealed[row][column] && !flagged[row][column]) {
                revealCell(row, column);
            }
        }
    }

    private class FlagMouseListener extends MouseAdapter {
        private int row;
        private int column;

        public FlagMouseListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                if (!revealed[row][column]) {
                    if (!flagged[row][column] && flagsRemaining > 0) {
                        flagged[row][column] = true;
                        cells[row][column].setText("F");
                        flagsRemaining--;
                    } else if (flagged[row][column]) {
                        flagged[row][column] = false;
                        cells[row][column].setText("");
                        flagsRemaining++;
                    }
                }
            }
        }
    }

    private void revealMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mines[i][j]) {
                    cells[i][j].setText("M");
                    cells[i][j].setEnabled(false);
                }
            }
        }
    }

    private boolean hasWon() {
        int unrevealedCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!revealed[i][j] && !mines[i][j]) {
                    unrevealedCount++;
                }
            }
        }
        return unrevealedCount == 0;
    }
}
