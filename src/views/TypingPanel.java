package views;

import javax.swing.*;
import java.awt.*;

/**
 * Panel trybu wpisywania odpowiedzi.
 * Wyświetla słowo do przetłumaczenia, pole do wpisania odpowiedzi
 * oraz informację o postępie w sesji nauki.
 */
public class TypingPanel extends JPanel {

    private final JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextField inputField = new JTextField();
    private final JButton checkButton = new JButton("Sprawdź");
    private final JButton backBtn = new JButton("Wróć");
    private final JLabel progressLabel = new JLabel("", SwingConstants.RIGHT);

    public TypingPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // ===== TOP =====
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        wordLabel.setFont(wordLabel.getFont().deriveFont(Font.BOLD, 40f));
        top.add(wordLabel, BorderLayout.CENTER);

        progressLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        progressLabel.setForeground(Color.GRAY);
        top.add(progressLabel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputField.setFont(inputField.getFont().deriveFont(26f));

        JPanel center = new JPanel(new BorderLayout(10, 10));
        inputWrapper.setOpaque(false);
        inputField.setPreferredSize(new Dimension(300, 100));
        inputWrapper.add(inputField);

        center.add(inputWrapper, BorderLayout.CENTER);
        center.add(new JLabel("Wpisz tłumaczenie:"), BorderLayout.NORTH);
        center.add(inputWrapper, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // ===== BOTTOM =====
        JPanel bottom = new JPanel();
        bottom.add(backBtn, BorderLayout.SOUTH);
        bottom.add(checkButton, BorderLayout.CENTER);
        bottom.setOpaque(false);


        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Ustawia aktualne słowo do przetłumaczenia oraz czyści pole wejściowe.
     *
     * @param word słowo wyświetlane użytkownikowi
     */
    public void setWord(String word) {
        wordLabel.setText(word);
        inputField.setText("");
        inputField.requestFocusInWindow();
    }

    /**
     * Aktualizuje informację o postępie w trybie nauki.
     *
     * @param current indeks aktualnego pytania
     * @param total   łączna liczba pytań
     */
    public void setProgress(int current, int total) {
        progressLabel.setText((current + 1) + " / " + total);
    }

    /**
     * Zwraca odpowiedź wpisaną przez użytkownika.
     *
     * @return tekst wpisany w pole odpowiedzi (bez białych znaków)
     */
    public String getInput() {
        return inputField.getText().trim();
    }

    /**
     * Rejestruje akcję sprawdzenia odpowiedzi.
     * Akcja wywoływana jest po kliknięciu przycisku lub naciśnięciu Enter.
     *
     * @param action logika sprawdzania odpowiedzi
     */
    public void onCheck(Runnable action) {
        checkButton.addActionListener(e -> action.run());
        inputField.addActionListener(e -> action.run());
    }

    /**
     * Rejestruje akcję powrotu do poprzedniego widoku.
     *
     * @param action logika powrotu
     */
    public void setOnBack(Runnable action) {
        backBtn.addActionListener(e -> action.run());
    }
}
