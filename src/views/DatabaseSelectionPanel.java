package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Widok wyboru zestawu słów do sesji nauki.
 *
 * Panel prezentuje listę dostępnych baz danych (zestawów słów)
 * oraz udostępnia akcje rozpoczęcia nowej sesji lub przejścia
 * do trybu powtórkowego.
 *
 */
public class DatabaseSelectionPanel extends JPanel {

    private final JList<String> fileList;
    private final JButton loadButton = new JButton("Załaduj");
    private final JButton reviewButton = new JButton("Review");

    /**
     * Tworzy panel wyboru zestawu słów.
     *
     * @param files lista dostępnych nazw zestawów
     */
    public DatabaseSelectionPanel(List<String> files) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Wybierz zestaw słów", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));

        fileList = new JList<>(files.toArray(new String[0]));
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel buttons = new JPanel();
        buttons.add(loadButton);
        buttons.add(reviewButton);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(fileList), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    /**
     * Zwraca aktualnie wybrany zestaw słów.
     *
     * @return nazwa wybranego zestawu lub null,
     *         jeśli nie dokonano wyboru
     */
    public String getSelectedFile() {
        return fileList.getSelectedValue();
    }

    /**
     * Wyświetla komunikat błędu w oknie dialogowym.
     *
     * @param msg treść komunikatu
     */
    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Rejestruje akcję uruchamianą po kliknięciu przycisku „Załaduj”.
     *
     * @param l listener obsługujący rozpoczęcie sesji nauki
     */
    public void onLoadBtn(ActionListener l) {
        loadButton.addActionListener(l);
    }

    /**
     * Rejestruje akcję uruchamianą po kliknięciu przycisku „Review”.
     *
     * @param l listener obsługujący tryb powtórkowy
     */
    public void onReviewBtn(ActionListener l) {
        reviewButton.addActionListener(l);
    }
}
