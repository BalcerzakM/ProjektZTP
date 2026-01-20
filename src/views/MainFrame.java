package views;

import app.AppContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Główne okno aplikacji.
 * Odpowiada za:
 * - wyświetlanie aktualnego widoku aplikacji
 * - zarządzanie przełączaniem paneli za pomocą CardLayout
 * - obsługę zamykania aplikacji i zapis danych przed wyjściem.
 */
public class MainFrame extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);
    private final AppContext context;

    /**
     * Tworzy główne okno aplikacji.
     *
     * @param title   tytuł okna
     * @param context kontekst aplikacji wykorzystywany do zapisu danych przy zamykaniu
     */
    public MainFrame(String title, AppContext context) {
        super(title);

        this.context = context;

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                performShutdown();
            }
        });
        add(root);
    }

    private void performShutdown() {
        if(context != null) {
            context.saveToDbAndExit();
        }
        int result = JOptionPane.showOptionDialog(
                this,
                "Czy na pewno chcesz wyjść?",
                "Wyjście",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"TAK", "NIE"},
                "NIE"
        );
        if (result == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }

    }

    /**
     * Wyświetla wskazany widok w głównym oknie aplikacji.
     *
     * Metoda zastępuje aktualnie wyświetlany panel nowym widokiem
     * i przełącza układ na podaną nazwę.
     *
     * @param panel panel do wyświetlenia
     * @param name  identyfikator widoku
     */
    public void showView(JPanel panel, String name) {
        root.removeAll();
        root.add(panel, name);
        layout.show(root, name);
        revalidate();
        repaint();
    }
}
