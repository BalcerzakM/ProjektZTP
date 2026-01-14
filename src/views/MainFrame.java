package views;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    public MainFrame(String title) {
        super(title);
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(root);
    }

    public void showView(JPanel panel, String name) {
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
