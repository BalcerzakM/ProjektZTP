package views;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatedPasswordField;
    private final JButton registerBtn;
    private final JButton goToLoginPanelBtn;


    public RegisterPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel registerPanelLabel = new JLabel("Wprowadź dane aby się zarejestrować");
        JLabel usernameLabel = new JLabel("Podaj nową nazwę użytkownika:");
        JLabel passwordLabel = new JLabel("Podaj nowe hasło:");
        JLabel repeatedPasswordLabel = new JLabel("Powtórz nowe hasło:");
        JLabel doYouHaveAcoountLabel = new JLabel("Masz już założone konto użytkownika?");

        JPanel loginButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        this.usernameField = new JTextField(15);
        this.passwordField = new JPasswordField(15);
        this.repeatedPasswordField = new JPasswordField(15);
        this.registerBtn = new JButton("ZAREJESTRUJ SIĘ");
        this.goToLoginPanelBtn = new JButton("PRZEJDŹ DO EKRANU LOGOWANIA");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        add(registerPanelLabel, gbc);

        gbc.gridy++;
        add(usernameLabel, gbc);

        gbc.gridy++;
        add(usernameField, gbc);

        gbc.gridy++;
        add(passwordLabel, gbc);

        gbc.gridy++;
        add(passwordField, gbc);

        gbc.gridy++;
        add(repeatedPasswordLabel, gbc);

        gbc.gridy++;
        add(repeatedPasswordField, gbc);

        gbc.gridy++;
        add(registerBtn, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(25, 5, 5, 5);
        loginButtonPanel.add(doYouHaveAcoountLabel);
        loginButtonPanel.add(goToLoginPanelBtn);
        add(loginButtonPanel, gbc);

    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRepeatedPassword() {
        return new String(repeatedPasswordField.getPassword());
    }

    public void onRegisterBtn(Runnable action) {
        registerBtn.addActionListener(e -> action.run());
    }

    public void onGoToLoginPanelBtn(Runnable action) {
        goToLoginPanelBtn.addActionListener(e -> action.run());
    }

}
