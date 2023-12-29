package game;

import gui.MinesweeperGUI;
import gui.StatusManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;



public class GameManager {
    private JPanel gamePanel;
    private final GameBoardFactory gameBoardFactory;
    private MinesweeperGUI gui;
    private GameBoard board;
    private StatusManager statusManager;

    public GameManager(MinesweeperGUI gui, StatusManager statusManager) {
        this.gui = gui;
        this.statusManager = statusManager;
        this.gameBoardFactory = new DefaultGameBoardFactory(statusManager);
        this.board = gameBoardFactory.createGameBoard(10,10, 10);
    }

    public void initializeGame() {
        // Crea el panel para el juego
        gamePanel = new JPanel(new BorderLayout());

        // Agrega el panel de estado en la parte superior
        gamePanel.add(statusManager.getStatusPanel(), BorderLayout.NORTH);

        // Agrega el tablero al panel del juego
        gamePanel.add(board.getBoardPanel(), BorderLayout.CENTER);

        // Agrega el panel del juego al ContentPane de la GUI
        gui.getContentPane().add(gamePanel, BorderLayout.CENTER);
    }

    public void startGame(int rows, int columns, int numMines) {
        // Restablecer el juego antes de iniciar uno nuevo
        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();

        // Reinicia los contadores de estado
        statusManager.resetStatus();

        // Inicia el conteo de banderas
        statusManager.updateFlagsRemaining(numMines);

        // Inicia el timer
        statusManager.startTimer();

        // Agrega el panel de estado en la parte superior
        gamePanel.add(statusManager.getStatusPanel(), BorderLayout.NORTH);

        // Crea un nuevo tablero utilizando la fábrica
        board = gameBoardFactory.createGameBoard(rows, columns, numMines);

        // Agrega el tablero al panel del juego
        gamePanel.add(board.getBoardPanel(), BorderLayout.CENTER);

        // Actualiza el tamaño de la GUI
        gui.updateBoardLayout(rows, columns);

        // Agrega el panel del juego al ContentPane de la GUI
        gui.getContentPane().add(gamePanel, BorderLayout.CENTER);

        // Hacer visible el nuevo tablero
        gui.validate();
    }
}
