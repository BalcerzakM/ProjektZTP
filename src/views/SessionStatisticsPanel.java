package views;

import services.observers.SessionStatistics;

import javax.swing.*;
import java.awt.*;

public class SessionStatisticsPanel extends JPanel {
    private final JLabel correctLabel = new JLabel();
    private final JLabel incorrectLabel = new JLabel();
    private final JLabel correctPercentLabel = new JLabel();
    private final JProgressBar accuracyBar = new JProgressBar(0, 100);
    private final JLabel maxSessionStreakLabel = new JLabel();

    public SessionStatisticsPanel() {
        setLayout(new GridLayout(0, 1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(correctLabel);
        add(incorrectLabel);
        add(correctPercentLabel);
        add(accuracyBar);
        add(maxSessionStreakLabel);
    }

    public void setStatistics(SessionStatistics stats) {
        if (stats.isFlashCardSession()) {
            correctLabel.setText("Przejrzane fiszki: " + stats.getFlashCardCount());
            incorrectLabel.setVisible(false);
            correctPercentLabel.setVisible(false);
            accuracyBar.setVisible(false);
            maxSessionStreakLabel.setVisible(false);
            return;
        }
        correctLabel.setText("Poprawne odpowiedzi: " + stats.getCorrectCount());
        incorrectLabel.setText("Niepoprawne odpowiedzi: " + stats.getIncorrectCount());
        accuracyBar.setValue(stats.getCorrectPercent());
        correctPercentLabel.setText("Skuteczność: " + stats.getCorrectPercent() + "%");
        if(stats.isPerfect()) {
            correctPercentLabel.setText(correctPercentLabel.getText() + " IDEALNIE!");
        }
        maxSessionStreakLabel.setText("Najlepszy streak: " + stats.getMaxSessionStreak());
    }

    public void showInDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Podsumowanie lekcji", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout());
        content.add(this, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);

        content.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.pack();
        dialog.setBounds(0, 0, parent.getWidth()-200, parent.getHeight()-200);
        dialog.setLocationRelativeTo(parent);
        dialog.getRootPane().setDefaultButton(okButton);
        dialog.setVisible(true);
    }

}
