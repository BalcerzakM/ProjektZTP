package views;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private final JButton learningSessionBtn = new JButton("UCZ SIĘ!");
    private final JButton wordSetCreatorBtn = new JButton("UTWÓRZ NOWY ZESTAW SŁÓWEK.");
    private final JButton userBtn = new JButton("ZARZĄDZAJ SWOIM KONTEM URZTKOWNIKA");
    private final JButton statisticsBtn = new JButton("ZOBACZ STATYSTYKI");
    private final JButton changeWordSetBtn = new JButton("ZMIEŃ ZESTAW SŁÓWEK");
    private final JButton logOutBtn = new JButton("WYLOGUJ SIĘ");

    public MainMenuPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel modes = new JPanel(new GridLayout(6, 1, 10,10));
        modes.add(learningSessionBtn);
        modes.add(wordSetCreatorBtn);
        modes.add(userBtn);
        modes.add(statisticsBtn);
        modes.add(changeWordSetBtn);
        modes.add(logOutBtn);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        add(buttonPanel, BorderLayout.SOUTH);
        add(modes, BorderLayout.CENTER);
    }



    public void onLearningSessionBtn(Runnable action) {
        learningSessionBtn.addActionListener(e -> action.run());
    }

    public void onWordSetCreatorBtn(Runnable action) {
        wordSetCreatorBtn.addActionListener(e -> action.run());
    }

    public void onUserBtn(Runnable action) {
        userBtn.addActionListener(e -> action.run());
    }

    public void onStatisticsBtn(Runnable action) {
        statisticsBtn.addActionListener(e -> action.run());
    }

    public void onChangeWordSetBtn(Runnable action) {
        changeWordSetBtn.addActionListener(e -> action.run());
    }

    public void onLogOutBtn(Runnable action) {
        logOutBtn.addActionListener(e -> action.run());
    }

}
