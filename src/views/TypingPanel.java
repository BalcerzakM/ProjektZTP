package views;

import javax.swing.*;
import java.awt.*;

public class TypingPanel extends JPanel {

    private final JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextField inputField = new JTextField();
    private final JButton checkButton = new JButton("Sprawdź");

    public TypingPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        wordLabel.setFont(wordLabel.getFont().deriveFont(Font.BOLD, 20f));
        add(wordLabel, BorderLayout.NORTH);

        inputField.setFont(inputField.getFont().deriveFont(16f));

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(new JLabel("Wpisz tłumaczenie:"), BorderLayout.NORTH);
        center.add(inputField, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
        add(checkButton, BorderLayout.SOUTH);
    }

    public void setWord(String word) {
        wordLabel.setText(word);
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    public String getInput() {
        return inputField.getText().trim();
    }

    public void onCheck(Runnable action) {
        checkButton.addActionListener(e -> action.run());
        inputField.addActionListener(e -> action.run()); // ENTER
    }
}
