package views;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.List;

public class MillionairePanel extends JPanel {

    private final JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton[] optionButtons = new JButton[4];
    private final JButton backBtn = new JButton("Wróć");
    private final JLabel progressLabel = new JLabel();
    private Runnable onBack;


    public MillionairePanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        questionLabel.setFont(questionLabel.getFont().deriveFont(Font.BOLD, 18f));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 15, 15));

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(optionButtons[i].getFont().deriveFont(15f));
            optionsPanel.add(optionButtons[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);

        progressLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        progressLabel.setForeground(Color.GRAY);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(questionLabel, BorderLayout.CENTER);
        top.add(progressLabel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });

    }

    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    public void setOptions(List<String> options, Consumer<String> onSelect) {
        for (int i = 0; i < 4; i++) {
            String option = options.get(i);
            optionButtons[i].setText(option);
            optionButtons[i].addActionListener(e -> onSelect.accept(option));
        }
    }


    public void setProgress(int current, int total) {
        progressLabel.setText((current + 1) + " / " + total);
    }


    /**
     * Metoda Powrotu do ostatniego widoku i zapisania Memnto
     */
    public void setOnBack(Runnable action) {backBtn.addActionListener(e -> action.run());}
}
