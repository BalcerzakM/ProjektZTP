package views;

import app.AppContext;
import app.AppState;
import controllers.Controller;
import controllers.DataInputController;
import controllers.DataInputControllerTerminal;
import controllers.LearningSessionController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {

    private final AppContext context;
    private AppState currentState;

    private final Map<AppState, Controller> controllers = new HashMap<>();

    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    public MainFrame(AppContext context, String title) {
        super(title);
        this.context = context;
        this.currentState = AppState.ChoosingDatabase;

        controllers.put(AppState.ChoosingDatabase, new DataInputController(this));
        controllers.put(AppState.LearningSession, new LearningSessionController(this));

        setTitle("ZTP Learning App");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(root);
    }

    public void start() {
        root.add(new JLabel("TEST – UI DZIAŁA"));
        layout.show(root, "TEST");
        setVisible(true);
        switchState(currentState);
        setVisible(true);
    }

    public void switchState(AppState newState) {
        this.currentState = newState;

        Controller controller = controllers.get(newState);
        if (controller == null) {
            System.err.println("Brak kontrolera dla: " + newState);
            return;
        }

        controller.run(context);
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
