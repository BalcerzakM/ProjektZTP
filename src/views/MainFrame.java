package views;

import app.AppContext;
import app.AppState;
import controllers.Controller;
import controllers.DataInputController;
import controllers.LearningSessionController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    public MainFrame(String title) {
        super(title);
        setTitle("ZTP Learning App");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(root);
    }

    public void setView(JPanel panel, String name) {
        root.removeAll();
        root.add(panel, name);
        layout.show(root, name);
        revalidate();
        repaint();
    }

    public void quit() {
        dispose();
    }
}
