package views;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.List;

public class MillionairePanel extends JPanel {

    private final JLabel questionLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton[] optionButtons = new JButton[4];

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
}
