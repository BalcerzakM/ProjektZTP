package views;

import models.LanguageCERFLevel;
import models.Statistics;
import models.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Set;

public class StatisticsPanel extends JPanel {
    private JLabel usernameLabel = new JLabel();
    private JLabel levelLabel = new JLabel();

    private JProgressBar levelProgressBar = new JProgressBar(0, 100);
    private JLabel progressLabel = new JLabel();

    private JLabel completedLessons = new JLabel();
    private JLabel correctAnswers = new JLabel();
    private JLabel incorrectAnswers = new JLabel();
    private JLabel longestStreak = new JLabel();
    private JLabel learnedWords = new JLabel();
    private JLabel perfectLessons = new JLabel();
    private JLabel totalFlashcards = new JLabel();

    private JButton backBtn = new JButton("Wróć");

    public StatisticsPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(buildUserPanel(), BorderLayout.NORTH);
        add(buildStatsPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }

    public void setUser(User user) {
        usernameLabel.setText("Użytkownik: " + user.getUsername());
        levelLabel.setText("Poziom języka: " + user.getLanguageLevel());
        progressLabel.setText("Postęp do następnego poziomu: ");
    }

    public void setStatistics(Statistics stats) {
        completedLessons.setText(String.valueOf(stats.getCompletedLessons()));
        correctAnswers.setText(String.valueOf(stats.getCorrectOverall()));
        incorrectAnswers.setText(String.valueOf(stats.getIncorrectOverall()));
        longestStreak.setText(String.valueOf(stats.getLongestStreak()));
        learnedWords.setText(String.valueOf(stats.getLearnedWordsAmount()));
        perfectLessons.setText(String.valueOf(stats.getPerfectLessons()));
        totalFlashcards.setText(String.valueOf(stats.getTotalFlashCards()));

    }

    public void setProgressBar(int percent) {
        levelProgressBar.setMinimum(0);
        levelProgressBar.setMaximum(100);
        levelProgressBar.setValue(percent);
        levelProgressBar.setString(percent + "%");
        levelProgressBar.setStringPainted(true);
    }

    public void onBack(Runnable action) {
        backBtn.addActionListener(e -> action.run());
    }

    private JPanel buildUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 5));
        panel.setBorder(new TitledBorder("Użytkownik"));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        leftPanel.add(usernameLabel);
        leftPanel.add(levelLabel);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));

        rightPanel.add(progressLabel, BorderLayout.NORTH);
        rightPanel.add(levelProgressBar, BorderLayout.CENTER);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new TitledBorder("Statystyki ogólne"));

        panel.add(new JLabel("Ukończone lekcje:"));
        panel.add(completedLessons);

        panel.add(new JLabel("Poprawne odpowiedzi:"));
        panel.add(correctAnswers);

        panel.add(new JLabel("Niepoprawne odpowiedzi:"));
        panel.add(incorrectAnswers);

        panel.add(new JLabel("Idealne lekcje:"));
        panel.add(perfectLessons);

        panel.add(new JLabel("Przerobione słowa:"));
        panel.add(learnedWords);

        panel.add(new JLabel("Przejrzane fiszki:"));
        panel.add(totalFlashcards);

        panel.add(new JLabel("Najlepszy streak:"));
        panel.add(longestStreak);

        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backBtn.setPreferredSize(new Dimension(120, 35));
        panel.add(backBtn);
        return panel;
    }
}
