public interface GameBoardFactory {
    GameBoard createGameBoard(MinesweeperGUI gui, int rows, int columns, int numMines);
}

