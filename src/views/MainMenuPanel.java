package views;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private final JLabel loggedUserLabel;

    private final JButton learningSessionBtn;
    private final JButton statisticsBtn;
    private final JButton logOutBtn;

    public MainMenuPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        this.loggedUserLabel = new JLabel("Zalogowano jako: ...", SwingConstants.CENTER);
        this.loggedUserLabel.setFont(new Font("Arial", Font.BOLD, 14));

        this.learningSessionBtn = new JButton("UCZ SIĘ!");
        this.statisticsBtn = new JButton("ZOBACZ STATYSTYKI");
        this.logOutBtn = new JButton("WYLOGUJ SIĘ");

        JPanel buttonsGrid = new JPanel(new GridLayout(5, 1, 10,10));
        buttonsGrid.add(learningSessionBtn);
        buttonsGrid.add(statisticsBtn);
        buttonsGrid.add(logOutBtn);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonsGrid);

        add(loggedUserLabel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    public void setWelcomeMessage(String username) {
        loggedUserLabel.setText("Zalogowano jako: " + username);
    }

    public void showLogOutMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Wylogowano cię pomyślnie.",
                "Informacja",
                JOptionPane.INFORMATION_MESSAGE);

    }

    public void onLearningSessionBtn(Runnable action) {
        learningSessionBtn.addActionListener(e -> action.run());
    }

    public void onStatisticsBtn(Runnable action) {
        statisticsBtn.addActionListener(e -> action.run());
    }

    public void onLogOutBtn(Runnable action) {
        logOutBtn.addActionListener(e -> action.run());
    }

}
