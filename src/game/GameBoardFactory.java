package game;

import gui.MinesweeperGUI;

public interface GameBoardFactory {
    GameBoard createGameBoard(int rows, int columns, int numMines);
}

