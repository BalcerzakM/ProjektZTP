package views;

import javax.swing.*;
import java.awt.*;

/**
 * Widok odpowiedzialny za logowanie użytkownika do aplikacji.
 *
 * Panel umożliwia wprowadzenie nazwy użytkownika i hasła,
 * wyświetlanie komunikatów o błędach logowania oraz
 * przekazanie akcji do kontrolera.
 */
public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginBtn;
    private final JButton goToRegisterPanelBtn;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel usernameLabel = new JLabel("Podaj nazwę użytkownika:");
        JLabel passwordLabel = new JLabel("Podaj hasło:");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.usernameField = new JTextField(15);
        this.passwordField = new JPasswordField(15);
        this.loginBtn = new JButton("ZALOGUJ SIĘ");
        this.goToRegisterPanelBtn = new JButton("ZAREJESTRUJ SIĘ");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridy++;
        add(usernameField, gbc);

        gbc.gridy++;
        add(passwordLabel, gbc);

        gbc.gridy++;
        add(passwordField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 5, 5, 5);
        buttonPanel.add(loginBtn);
        buttonPanel.add(goToRegisterPanelBtn);
        add(buttonPanel, gbc);

    }

    /**
     * Wyświetla komunikat o nieprawidłowym haśle.
     */
    public void showWrongPasswordError() {
        JOptionPane.showMessageDialog(
                this,
                "Nieprawidłowe hasło użytkownika. Spróbuj ponownie",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Wyświetla komunikat informujący, że użytkownik o podanej nazwie nie istnieje.
     */
    public void showNoUserExistError() {
        JOptionPane.showMessageDialog(
                this,
                "Nie znaleziono użytkownika o podanej nazwie. Zarejestruj się lub spróbuj ponownie.",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Wyświetla komunikat o pozostawieniu pustych pól formularza.
     */
    public void showEmptyFieldsError() {
        JOptionPane.showMessageDialog(
                this,
                "Wprowadź login i hasło.",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Wyświetla komunikat o pomyślnym zalogowaniu użytkownika.
     */
    public void showSuccessMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Zalogowano się pomyślnie.",
                "Informacja",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Zwraca nazwę użytkownika wprowadzoną w formularzu.
     *
     * @return nazwa użytkownika
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Zwraca hasło wprowadzone w formularzu.
     *
     * @return hasło użytkownika
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }


    /**
     * Rejestruje akcję wykonywaną po kliknięciu przycisku logowania.
     *
     * @param action akcja obsługująca logowanie
     */
    public void onLoginBtn(Runnable action) {
        loginBtn.addActionListener(e -> action.run());
    }

    /**
     * Rejestruje akcję przejścia do panelu rejestracji.
     *
     * @param action akcja zmiany widoku na rejestrację
     */
    public void onGoToRegisterPanelBtn(Runnable action) {
        goToRegisterPanelBtn.addActionListener(e -> action.run());
    }

}
