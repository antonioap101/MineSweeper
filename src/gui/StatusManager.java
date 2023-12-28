import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusManager {
    private JPanel statusPanel;
    private JLabel flagsLabel;
    private JLabel timeLabel;
    private int flagsRemaining;
    private Timer timer;
    private int timeElapsed;

    StatusManager() {

    }

    public StatusManager initializeStatus(){
        // Espaciado horizontal de 10 y vertical de 10
        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        flagsLabel = new JLabel("Banderas restantes: ");
        timeLabel = new JLabel("Tiempo transcurrido: ");

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
        flagsLabel.setText("Banderas restantes: " + flagsRemaining);
    }

    public void updateTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
        timeLabel.setText("Tiempo transcurrido: " + timeElapsed + " segundos");
    }

    public void resetStatus() {
        flagsRemaining = 0;
        timeElapsed = 0;
        flagsLabel.setText("Banderas restantes: ");
        timeLabel.setText("Tiempo transcurrido: ");
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
