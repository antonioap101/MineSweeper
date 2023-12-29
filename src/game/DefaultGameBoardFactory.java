package game;

import game.GameBoard;
import game.GameBoardFactory;
import gui.MinesweeperGUI;
import gui.StatusManager;

public class DefaultGameBoardFactory implements GameBoardFactory {
    private final StatusManager statusManager;

    public DefaultGameBoardFactory(StatusManager statusManager) {
        this.statusManager = statusManager;
    }

    @Override
    public GameBoard createGameBoard(int rows, int columns, int numMines) {
        return new GameBoard(statusManager, rows, columns, numMines);
    }
}