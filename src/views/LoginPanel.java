package views;

import javax.swing.*;
import java.awt.*;

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

    public void showWrongPasswordError() {
        JOptionPane.showMessageDialog(
                this,
                "Nieprawidłowe hasło użytkownika. Spróbuj ponownie",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showNoUserExistError() {
        JOptionPane.showMessageDialog(
                this,
                "Nie znaleziono użytkownika o podanej nazwie. Zarejestruj się lub spróbuj ponownie.",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showEmptyFieldsError() {
        JOptionPane.showMessageDialog(
                this,
                "Wprowadź login i hasło.",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Zalogowano się pomyślnie.",
                "Informacja",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void onLoginBtn(Runnable action) {
        loginBtn.addActionListener(e -> action.run());
    }

    public void onGoToRegisterPanelBtn(Runnable action) {
        goToRegisterPanelBtn.addActionListener(e -> action.run());
    }

}
