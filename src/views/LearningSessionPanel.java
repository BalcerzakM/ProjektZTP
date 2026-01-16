package views;

import javax.swing.*;
import java.awt.*;

public class LearningSessionPanel extends JPanel {

    private final JButton flashCardBtn = new JButton("Fiszki");
    private final JButton connectBtn = new JButton("Łączenie");
    private final JButton millionaireBtn = new JButton("Milionerzy");
    private final JButton typingBtn = new JButton("Wpisywanie");
    private final JButton backBtn = new JButton("Wróć");

    public LearningSessionPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel modes = new JPanel(new GridLayout(4, 1, 10,10));
        modes.add(flashCardBtn);
        modes.add(connectBtn);
        modes.add(millionaireBtn);
        modes.add(typingBtn);

        backBtn.setPreferredSize(new Dimension(90, 30));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);
        add(modes, BorderLayout.CENTER);
    }

    public int showQuestionMessage() {
        return JOptionPane.showOptionDialog(
                this,
                "Czy na pewno chcesz skończyć lekcję?",
                "Zakończyć lekcję?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"TAK", "NIE"},
                "NIE"
        );
    }

    public void onFlashCard(Runnable action) {
        flashCardBtn.addActionListener(e -> action.run());
    }

    public void onConnect(Runnable action) {
        connectBtn.addActionListener(e -> action.run());
    }

    public void onMillionaire(Runnable action) {
        millionaireBtn.addActionListener(e -> action.run());
    }

    public void onTyping(Runnable action) {
        typingBtn.addActionListener(e -> action.run());
    }

    public void onBack(Runnable action) {backBtn.addActionListener(e -> action.run());}
}


