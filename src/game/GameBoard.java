package game;

import gui.MinesweeperGUI;
import gui.StatusManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;


public class GameBoard {
    private final StatusManager statusManager;
    // Carga la imagen desde un archivo (ajusta la ruta según tu archivo de imagen)
    private ImageIcon darkCellIcon = new ImageIcon("assets/DarkCell.png");
    private ImageIcon brightCellIcon = new ImageIcon("assets/BrightCell.png");
    private ImageIcon darkRevCellIcon = new ImageIcon("assets/DarkRevealedCell.png");
    private ImageIcon brightRevCellIcon = new ImageIcon("assets/BrightRevealedCell.png");
    private ImageIcon FlaggedDarkCellIcon = new ImageIcon("assets/FlaggedDarkCell.png");
    private ImageIcon FlaggedBrightCellIcon = new ImageIcon("assets/FlaggedBrightCell.png");
    private ImageIcon BombDarkCellIcon = new ImageIcon("assets/bombs/DarkBomb.png");
    private ImageIcon BombBrightCellIcon = new ImageIcon("assets/bombs/BrightBomb.png");
    private Map<String, ImageIcon> numCellIcons = new HashMap<>();

    private Dimension cellSize = new Dimension(40, 40);

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


    public GameBoard(StatusManager statusManager, int rows, int columns, int numMines) {
        this.statusManager = statusManager;
        this.rows = rows;
        this.columns = columns;
        this.numMines = numMines;
        this.flagsRemaining = numMines;
        this.boardPanel = new JPanel(new GridLayout(rows, columns, 0, 0));

        // Agregar numCellIcons al diccionario
        for (int i = 1; i <= 8; i++) {
            String darkCellName = i + "D";
            String brightCellName = i + "B";
            String darkCellFilePath = "assets/numCells/" + i + "D.png";
            String brightCellFilePath = "assets/numCells/" + i + "B.png";
            numCellIcons.put(brightCellName, new ImageIcon(brightCellFilePath));
            numCellIcons.put(darkCellName, new ImageIcon(darkCellFilePath));
        }

        initializeBoard();
    }

    private void initializeBoard() {
        cells = new JButton[rows][columns];
        mines = new boolean[rows][columns];
        revealed = new boolean[rows][columns];
        flagged = new boolean[rows][columns];


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setMargin(new Insets(0, 0, 0, 0));
                cells[i][j].setBorderPainted(false);
                cells[i][j].setContentAreaFilled(false);
                cells[i][j].setFocusPainted(false); // Opcional: para no pintar el foco cuando el botón es clickeado
                cells[i][j].setOpaque(false);
                cells[i][j].setSize(cellSize);
                ImageIcon icon = ((i + j) % 2 == 0) ? darkCellIcon : brightCellIcon;
                cells[i][j].setIcon(new ImageIcon(
                        icon.getImage().getScaledInstance(cellSize.width, cellSize.height, Image.SCALE_SMOOTH)
                        ));

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
        //cells[row][column].setEnabled(false);
        ImageIcon icon = ((row + column) % 2 == 0) ? darkRevCellIcon : brightRevCellIcon;
        cells[row][column].setIcon(new ImageIcon(
                icon.getImage().getScaledInstance(cellSize.width, cellSize.height, Image.SCALE_SMOOTH)
        ));


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
            } else { // Casilla no es una mina, muestra el número correspondiente
                // Utiliza "D" para DarkCell o "B" para BrightCell según corresponda
                String iconName = count + (((row + column) % 2 == 0) ? "D" : "B");
                cells[row][column].setIcon(new ImageIcon(numCellIcons
                                .get(iconName)
                                .getImage()
                                .getScaledInstance(cellSize.width, cellSize.height, Image.SCALE_SMOOTH)
                ));

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
                        ImageIcon icon = ((row + column) % 2 == 0) ? FlaggedDarkCellIcon : FlaggedBrightCellIcon;
                        cells[row][column].setIcon(new ImageIcon(
                                icon.getImage().getScaledInstance(cellSize.width, cellSize.height, Image.SCALE_SMOOTH)
                        ));
                        decreaseFlagsRemaining();
                    } else if (flagged[row][column]) {
                        flagged[row][column] = false;
                        ImageIcon icon = ((row + column) % 2 == 0) ? darkCellIcon : brightCellIcon;
                        cells[row][column].setIcon(new ImageIcon(
                                icon.getImage().getScaledInstance(cellSize.width, cellSize.height, Image.SCALE_SMOOTH)
                        ));
                        increaseFlagsRemaining();
                    }
                }
            }
        }
    }

    public void increaseFlagsRemaining() {
        flagsRemaining++;
        statusManager.updateFlagsRemaining(flagsRemaining);
    }

    public void decreaseFlagsRemaining() {
        flagsRemaining--;
        statusManager.updateFlagsRemaining(flagsRemaining);
    }


    private void revealMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mines[i][j]) {
                    ImageIcon icon = ((i + j) % 2 == 0) ? BombDarkCellIcon : BombBrightCellIcon;
                    cells[i][j].setIcon(new ImageIcon(
                            icon.getImage().getScaledInstance(cellSize.width, cellSize.height, Image.SCALE_SMOOTH)
                    ));
                    //cells[i][j].setEnabled(false);
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
