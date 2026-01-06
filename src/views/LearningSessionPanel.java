package views;

import javax.swing.*;
import java.awt.*;

public class LearningSessionPanel extends JPanel {

    private JButton flashCardBtn = new JButton("Flashcards");
    private JButton connectBtn = new JButton("Connect");
    private JButton millionaireBtn = new JButton("Millionaire");
    private JButton typingBtn = new JButton("Typing");

    public LearningSessionPanel() {
        setLayout(new GridLayout(4, 1, 10, 10));

        add(flashCardBtn);
        add(connectBtn);
        add(millionaireBtn);
        add(typingBtn);
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
}


