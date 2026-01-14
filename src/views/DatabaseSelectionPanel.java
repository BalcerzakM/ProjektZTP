package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DatabaseSelectionPanel extends JPanel {

    private final JList<String> fileList;
    private final JButton loadButton = new JButton("Załaduj");
    private final JButton reviewButton = new JButton("Review");

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

    // 🔽 API widoku
    public String getSelectedFile() {
        return fileList.getSelectedValue();
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    // 🔽 rejestracja listenerów
    public void onLoadBtn(ActionListener l) {
        loadButton.addActionListener(l);
    }

    public void onReviewBtn(ActionListener l) {
        reviewButton.addActionListener(l);
    }
}
