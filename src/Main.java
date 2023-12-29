import gui.MinesweeperGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        SwingUtilities.invokeLater(() -> {
            MinesweeperGUI game = new MinesweeperGUI();
            game.setVisible(true);
        });
    }

}