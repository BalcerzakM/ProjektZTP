package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FlashCardPanel extends JPanel {

    private final JLabel card = new JLabel("", SwingConstants.CENTER);
    private Runnable onClick;

    public FlashCardPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30));

        card.setPreferredSize(new Dimension(500, 300));
        card.setOpaque(true);
        card.setBackground(new Color(50, 50, 50));
        card.setForeground(Color.WHITE);
        card.setFont(new Font("Arial", Font.BOLD, 32));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        add(card);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });
    }

    public void setText(String text) {
        card.setText(text);
    }

    public void setOnClick(Runnable action) {
        this.onClick = action;
    }
}
