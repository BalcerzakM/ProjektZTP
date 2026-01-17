package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FlashCardPanel extends JPanel {

    private final JLabel card = new JLabel("", SwingConstants.CENTER);
    private final JButton backBtn = new JButton("Wróć");
    private final JButton prevBtn = new JButton("⬅");
    private final JButton nextBtn = new JButton("➡");
    private Runnable onClick;
    private Runnable onBack;
    private final JLabel progressLabel = new JLabel();

    public FlashCardPanel()  {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        card.setPreferredSize(new Dimension(500, 300));
        card.setOpaque(true);
        card.setBackground(Color.LIGHT_GRAY);
        card.setForeground(Color.BLACK);
        card.setFont(new Font("Arial", Font.BOLD, 32));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(card);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        bottom.add(prevBtn, BorderLayout.WEST);
        bottom.add(backBtn, BorderLayout.CENTER);
        bottom.add(nextBtn, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);

        progressLabel.setForeground(Color.GRAY);
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(progressLabel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });

        backBtn.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
    }

    public void setText(String text) {
        card.setText(text);
    }

    public void setOnClick(Runnable action) {
        this.onClick = action;
    }

    public void setOnNext(Runnable action) {
        nextBtn.addActionListener(e -> action.run());
    }

    public void setOnPrev(Runnable action) {
        prevBtn.addActionListener(e -> action.run());
    }

    /**
     * Metoda Powrotu do ostatniego widoku i zapisania Memnto
     */
    public void setOnBack(Runnable action) {backBtn.addActionListener(e -> action.run());}

    /**
     * Metoda do wyświetlania, na której fiszcze jest użytkownik
     * @param current aktualna fiszka
     * @param total wszystkie w WordSet
     */
    public void setProgress(int current, int total) {
        progressLabel.setText((current + 1) + " / " + total);
    }

}
