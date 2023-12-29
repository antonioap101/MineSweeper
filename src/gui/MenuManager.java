package gui;

import gui.MinesweeperGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManager {
    private MinesweeperGUI gui;


    public MenuManager(MinesweeperGUI gui) {
        this.gui = gui;
    }

    public void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        gui.setJMenuBar(menuBar);

        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        JMenuItem easyMenuItem = new JMenuItem("Easy");
        JMenuItem mediumMenuItem = new JMenuItem("Medium");
        JMenuItem hardMenuItem = new JMenuItem("Hard");
        JMenuItem customMenuItem = new JMenuItem("Custom");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        easyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.startGame(10, 10, 10);
            }
        });

        mediumMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.startGame(18, 18, 40);
            }
        });

        hardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.startGame(24, 24, 99);
            }
        });

        customMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Puedes implementar aquí una ventana de diálogo para personalizar la configuración del juego
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        optionsMenu.add(easyMenuItem);
        optionsMenu.add(mediumMenuItem);
        optionsMenu.add(hardMenuItem);
        optionsMenu.add(customMenuItem);
        optionsMenu.addSeparator();
        optionsMenu.add(exitMenuItem);
    }
}
