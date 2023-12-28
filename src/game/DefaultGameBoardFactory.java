import game.GameBoard;
import game.GameBoardFactory;
import gui.MinesweeperGUI;

public class DefaultGameBoardFactory implements GameBoardFactory {
    @Override
    public GameBoard createGameBoard(MinesweeperGUI gui, int rows, int columns, int numMines) {
        return new GameBoard(gui, rows, columns, numMines);
    }
}