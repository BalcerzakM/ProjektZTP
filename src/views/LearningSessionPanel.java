package views;

import javax.swing.*;
import java.awt.*;

public class LearningSessionPanel extends JPanel {

    private JButton flashCardBtn = new JButton("Flashcards");
    private JButton connectBtn = new JButton("Connect");
    private JButton millionaireBtn = new JButton("Millionaire");
    private JButton typingBtn = new JButton("Typing");
    private JButton backBtn = new JButton("Wróć");

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


