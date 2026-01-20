package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Widok trybu łączenia par (Connect).
 *
 * Panel prezentuje dwie listy elementów, pomiędzy którymi
 * użytkownik wybiera pary do połączenia. Odpowiada wyłącznie
 * za warstwę prezentacji oraz zbieranie akcji użytkownika.
 */
public class ConnectPanel extends JPanel {

    private final JList<String> leftList;
    private final JList<String> rightList;
    private final JButton checkBtn = new JButton("Połącz");
    private final JButton backBtn = new JButton("Wróć");
    private Runnable onBack;

    /**
     * Tworzy panel trybu łączenia z dwiema listami elementów.
     *
     * @param left lista elementów wyświetlanych po lewej stronie
     * @param right lista elementów wyświetlanych po prawej stronie
     */
    public ConnectPanel(List<String> left, List<String> right) {

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ===== TYTUŁ =====
        JLabel title = new JLabel("Tryb łączenia", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        // ===== LISTY =====
        leftList = createList(left);
        rightList = createList(right);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        listsPanel.add(createColumn("Lewo", leftList));
        listsPanel.add(createColumn("Prawo", rightList));

        add(listsPanel, BorderLayout.CENTER);

        // ===== PRZYCISK =====
        checkBtn.setFont(checkBtn.getFont().deriveFont(Font.BOLD));
        JPanel btnPanel = new JPanel();
        btnPanel.add(backBtn);
        btnPanel.add(checkBtn);


        add(btnPanel, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
    }

    // ===== POMOCNICZE =====

    private JList<String> createList(List<String> data) {
        JList<String> list = new JList<>(data.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(4);
        list.setFixedCellHeight(30);
        return list;
    }

    private JPanel createColumn(String title, JList<String> list) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        return panel;
    }

    // ===== API DLA CONTROLLERA =====

    /**
     * Zwraca indeks zaznaczonego elementu z lewej listy.
     *
     * @return indeks zaznaczenia lub -1, jeśli brak wyboru
     */
    public int getLeftIndex() {
        return leftList.getSelectedIndex();
    }

    /**
     * Zwraca indeks zaznaczonego elementu z prawej listy.
     *
     * @return indeks zaznaczenia lub -1, jeśli brak wyboru
     */
    public int getRightIndex() {
        return rightList.getSelectedIndex();
    }

    /**
     * Rejestruje akcję wywoływaną po kliknięciu przycisku „Połącz”.
     *
     * @param action logika obsługi sprawdzenia pary
     */
    public void onCheck(Runnable action) {
        checkBtn.addActionListener(e -> action.run());
    }

    /**
     * Aktualizuje zawartość obu list po zmianie stanu trybu.
     *
     * @param left nowa lista elementów po lewej stronie
     * @param right nowa lista elementów po prawej stronie
     */
    public void updateLists(List<String> left, List<String> right) {
        leftList.setListData(left.toArray(new String[0]));
        rightList.setListData(right.toArray(new String[0]));
    }

    /**
     * Rejestruje akcję powrotu do poprzedniego widoku.
     *
     * Metoda wykorzystywana przez kontroler do zapisania
     * stanu sesji (Memento) i zakończenia trybu nauki.
     *
     * @param action logika wykonywana przy powrocie
     */
    public void setOnBack(Runnable action) {backBtn.addActionListener(e -> action.run());}
}

