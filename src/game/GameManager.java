import javax.swing.*;
import java.awt.*;

public class GameManager {
    private final JPanel gamePanel;
    private MinesweeperGUI gui;
    private GameBoard board;
    private StatusManager statusManager;


    public GameManager(MinesweeperGUI gui, GameBoard board, StatusManager statusManager) {
        this.board = board;
        this.statusManager = statusManager;

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
        // Limpiar el tablero existente
        gui.getContentPane().removeAll();
        gui.revalidate();
        gui.repaint();

        // Reinicia los contadores de estado
        statusManager.resetStatus();

        // Configurar el nuevo tablero
        board.initializeBoard(rows, columns, numMines);

        // Actualizar el layout del GUI para el nuevo tablero
        gui.updateBoardLayout(rows, columns);

        // Hacer visible el nuevo tablero
        gui.validate();
    }

    public int getFlagsRemaining() {
        return board.getFlagsRemaining();
    }

    public void decreaseFlagsRemaining() {
        statusManager.updateFlagsRemaining(board.getFlagsRemaining());
    }

    public void increaseFlagsRemaining() {
        statusManager.updateFlagsRemaining(board.getFlagsRemaining());
    }

    // Métodos para actualizar los contadores en el administrador de estado
    public void updateFlagsRemainingLabel(int flagsRemaining) {
        statusManager.updateFlagsRemaining(flagsRemaining);
    }

    public void updateTimeElapsedLabel(int timeElapsed) {
        statusManager.updateTimeElapsed(timeElapsed);
    }
}
