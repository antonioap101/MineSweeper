package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StatusManager {
    private ImageIcon FlagStatusIcon = new ImageIcon("assets/FlagStatus.png");
    private ImageIcon TimeStatusIcon = new ImageIcon("assets/TimeStatus.png");

    private JPanel statusPanel;
    private JLabel flagsLabel;
    private JLabel timeLabel;
    private int flagsRemaining;
    private Timer timer;
    private int timeElapsed;

    StatusManager() {
        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        flagsLabel = new JLabel(FlagStatusIcon);
        timeLabel = new JLabel(TimeStatusIcon);

        // Añade texto después de los iconos
        flagsLabel.setText(flagsLabel.getText() + flagsRemaining);
        timeLabel.setText(timeLabel.getText() + timeElapsed );
    }


    public StatusManager initializeStatus(){
        // Inicializar contadores
        flagsRemaining = 0;
        timeElapsed = 0;

        statusPanel.add(flagsLabel);
        statusPanel.add(timeLabel);

        return this;
    }

    public JPanel getStatusPanel() {
        return statusPanel;
    }

    public void updateFlagsRemaining(int flagsRemaining) {
        this.flagsRemaining = flagsRemaining;

        // Crea una imagen compuesta
        BufferedImage combinedImage = new BufferedImage(
                FlagStatusIcon.getIconWidth(), FlagStatusIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtiene el Graphics2D de la imagen compuesta
        Graphics2D g2d = combinedImage.createGraphics();

        // Dibuja la imagen original
        FlagStatusIcon.paintIcon(null, g2d, 0, 0);

        // Establece la fuente y el color del texto
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLACK); // Color del texto

        // Calcula la posición para centrar el texto
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(String.valueOf(flagsRemaining));
        int x = (combinedImage.getWidth() - textWidth) / 2 + 15;
        int y = (combinedImage.getHeight() + fm.getAscent()) / 2;

        // Dibuja el texto sobre la imagen
        g2d.drawString(String.valueOf(flagsRemaining), x, y);

        // Finaliza el Graphics2D
        g2d.dispose();

        // Establece la imagen compuesta en el JLabel
        flagsLabel.setIcon(new ImageIcon(combinedImage));
    }


    public void updateTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;

        // Crea una imagen compuesta
        BufferedImage combinedImage = new BufferedImage(
                TimeStatusIcon.getIconWidth(), TimeStatusIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        // Obtiene el Graphics2D de la imagen compuesta
        Graphics2D g2d = combinedImage.createGraphics();

        // Dibuja la imagen original
        TimeStatusIcon.paintIcon(null, g2d, 0, 0);

        // Establece la fuente y el color del texto
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLACK); // Color del texto

        // Calcula la posición para centrar el texto
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(String.valueOf(timeElapsed));
        int x = (combinedImage.getWidth() - textWidth) / 2 + 15;
        int y = (combinedImage.getHeight() + fm.getAscent()) / 2;

        // Dibuja el texto sobre la imagen
        g2d.drawString(String.valueOf(timeElapsed), x, y);

        // Finaliza el Graphics2D
        g2d.dispose();

        // Establece la imagen compuesta en el JLabel
        timeLabel.setIcon(new ImageIcon(combinedImage));
    }

    public void resetStatus() {
        flagsRemaining = 0;
        timeElapsed = 0;
        flagsLabel.setText("");
        timeLabel.setText("");
    }

    // Métodos para controlar el timer
    public void startTimer() {
        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeElapsed++;
                    updateTimeElapsed(timeElapsed);
                }
            });
        }
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
}
