package views;

import observers.SessionStatistics;

import javax.swing.*;
import java.awt.*;

public class SessionStatisticsPanel extends JPanel {
    private JLabel correctLabel = new JLabel();
    private JLabel incorrectLabel = new JLabel();
    private JLabel correctPercentLabel = new JLabel();
    private JProgressBar accuracyBar = new JProgressBar(0, 100);
    private JLabel maxSessionStreakLabel = new JLabel();

    public SessionStatisticsPanel() {
        setLayout(new GridLayout(0, 1, 5, 5));
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
}
