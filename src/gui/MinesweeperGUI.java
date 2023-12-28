import game.GameBoard;
import game.GameManager;

import javax.swing.*;
import java.awt.*;

public class MinesweeperGUI extends JFrame {
    private JPanel mainPanel;
    private GameBoard board;
    private MenuManager menuManager;
    private GameManager gameManager;
    private StatusManager statusManager; // Nuevo administrador de estado

    public MinesweeperGUI() {
        mainPanel = new JPanel(new BorderLayout()); // Crea el panel principal con BorderLayout
        setContentPane(mainPanel); // Configura el panel principal como contenido del JFrame


        board = new GameBoard(this);
        statusManager = new StatusManager().initializeStatus();
        menuManager = new MenuManager(this);
        gameManager = new GameManager(this, board, statusManager);

        initializeWindow();
        statusManager.initializeStatus();
        menuManager.initializeMenu();

        //startGame(10, 10, 20); // Inicia un juego predeterminado
    }

    private void initializeWindow() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateBoardLayout(int rows, int columns) {
        setLayout(new GridLayout(rows, columns));
        setSize(50 * columns, 50 * rows);
        setLocationRelativeTo(null);
    }

    // Método público para iniciar un nuevo juego, llamado desde MenuManager
    public void startGame(int rows, int columns, int numMines) {
        gameManager.startGame(rows, columns, numMines);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinesweeperGUI game = new MinesweeperGUI();
            game.setVisible(true);
        });
    }
}
